package com.flav.cinema_service_invoice.invoice.application;

import com.flav.cinema_service_invoice.client.IInventoryClientFeign;
import com.flav.cinema_service_invoice.client.IMovieClientFeign;
import com.flav.cinema_service_invoice.invoice.domain.constants.InvoiceConstants;
import com.flav.cinema_service_invoice.invoice.domain.dtos.reponse.InvoiceResponseDTO;
import com.flav.cinema_service_invoice.client.dtos.MovieResponseDTO;
import com.flav.cinema_service_invoice.invoice.domain.dtos.request.InvoiceRequestDTO;
import com.flav.cinema_service_invoice.invoice.domain.entity.Invoice;
import com.flav.cinema_service_invoice.invoice.domain.entity.InvoiceItem;
import com.flav.cinema_service_invoice.invoice.domain.exceptions.InvoiceNotFound;
import com.flav.cinema_service_invoice.invoice.domain.exceptions.NotMovieTicket;
import com.flav.cinema_service_invoice.invoice.domain.mappers.IInvoiceMapper;
import com.flav.cinema_service_invoice.invoice.domain.repository.IInvoiceRepository;
import com.flav.cinema_service_invoice.invoice.domain.services.IInvoiceService;
import com.flav.cinema_service_invoice.invoice.persistence.repository.IInvoiceItemRepositoryJpa;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Log4j2
@Service
public class InvoiceServicesImpl implements IInvoiceService {

    private final IInvoiceRepository repository;
    private final IInvoiceItemRepositoryJpa invoiceItemRepository;
    private final IMovieClientFeign movieClientFeign;
    private final IInventoryClientFeign inventoryClientFeign;
    private final IInvoiceMapper mapper;

    public InvoiceServicesImpl(IInvoiceRepository repository, IInvoiceItemRepositoryJpa invoiceItemRepository,
                               IMovieClientFeign movieClientFeign, IInventoryClientFeign inventoryClientFeign,
                               IInvoiceMapper mapper) {
        this.repository = repository;
        this.invoiceItemRepository = invoiceItemRepository;
        this.movieClientFeign = movieClientFeign;
        this.inventoryClientFeign = inventoryClientFeign;
        this.mapper = mapper;
    }

    @Override
    public List<InvoiceResponseDTO> findAll() {
        List<Invoice> list = repository.findAll()
                            .stream()
                            .map(invoice -> {
                                invoice.getInvoiceItem()
                                        .stream()
                                        .map(invoiceItem -> {
                                            //makes a call to the movies api to get the movie
                                            MovieResponseDTO movie = movieClientFeign
                                                    .getOneMovie(invoiceItem.getIdMovie()).getBody();

                                            //add movie to invoice
                                            invoiceItem.setMovie(movie);

                                            return invoiceItem;
                                        }).toList();
                                return invoice;
                            })
                            .toList();

        return list
                .stream()
                .map(item -> {
                    log.info("Successful request in class | InvoiceServicesImpl | findAll");
                    return mapper.toResponseDTO(item);
                })
                .toList();
    }

    @Override
    public InvoiceResponseDTO findOne(Long idInvoice) {
        Invoice invoiceDB = repository.findOne(idInvoice).orElseThrow(() -> {
            log.error(String.format("Request error in class | InvoiceServicesImpl | findOne | invoice id %s not found",
                    idInvoice));
            return new InvoiceNotFound(String.format(InvoiceConstants.INVOICE_NOT_FOUND, idInvoice),
                    HttpStatus.NOT_FOUND);
        });

        invoiceDB.getInvoiceItem()
                .stream()
                .map(item -> {
                    //makes a call to the movies api to get the movie
                    MovieResponseDTO movie = movieClientFeign.getOneMovie(item.getIdMovie()).getBody();

                    //add movie to invoice
                    item.setMovie(movie);

                    return item;
                })
                .toList();

        log.info("Successful request in class | InvoiceServicesImpl | findOne");
        return mapper.toResponseDTO(invoiceDB);
    }

    @Override
    public InvoiceResponseDTO create(InvoiceRequestDTO invoice) {
        //makes a call to the movies api to get the movie by id
        var list = movieClientFeign.getAllMovieById(
                invoice.getInvoiceItem()
                        .stream()
                        .map(InvoiceItem::getIdMovie)
                        .toList()
                ).getBody();

        //get the price of the movies for add to invoice
        var total = list.stream()
                        .peek(invoiceItem -> {
                            //check if the number of ticket is 0
                            if(invoiceItem.getTickets().getNumberOfTickets() == 0) {
                                log.error(String.format("Request error in class | InvoiceServicesImpl | create | movie id %s not ticket",
                                        invoiceItem.getTickets().getNumberOfTickets()));

                                throw new NotMovieTicket(
                                        String.format(InvoiceConstants.NOT_MOVIE_TICKET, invoiceItem.getId()),
                                        HttpStatus.BAD_REQUEST);
                            }
                        })
                        .map(MovieResponseDTO::getPrice)
                        .reduce(0.0, (cum, curr) -> cum + curr);

        //create a new invoice
        Invoice newInvoice = Invoice.builder()
                .createAt(new Date())
                .code(UUID.randomUUID().toString())
                .amount(invoice.getInvoiceItem().size())
                .total(total)
                .invoiceItem(invoice.getInvoiceItem())
                .build();

        //decrease inventory tickets in 1 for movie
        for(var movie: invoice.getInvoiceItem()) {
            inventoryClientFeign.takeOutStock(Math.toIntExact(movie.getIdMovie()));
        }

        //save invoice and return
        invoiceItemRepository.saveAll(invoice.getInvoiceItem());
        Long response = repository.create(newInvoice).getId();

        log.info("Successful request in class | InvoiceServicesImpl | create | decrease 1 ticket movie in inventory");
        log.info("Successful request in class | InvoiceServicesImpl | create");
        return (findOne(response));
    }

}
