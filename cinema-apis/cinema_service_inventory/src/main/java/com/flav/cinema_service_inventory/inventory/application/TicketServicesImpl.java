package com.flav.cinema_service_inventory.inventory.application;

import com.flav.cinema_service_inventory.inventory.domain.constants.TicketConstants;
import com.flav.cinema_service_inventory.inventory.domain.dtos.reponse.TicketResponseDTO;
import com.flav.cinema_service_inventory.inventory.domain.dtos.request.TicketRequestDTO;
import com.flav.cinema_service_inventory.inventory.domain.entity.Ticket;
import com.flav.cinema_service_inventory.inventory.domain.exceptions.NotTicketAvailable;
import com.flav.cinema_service_inventory.inventory.domain.exceptions.TicketMovieNotFound;
import com.flav.cinema_service_inventory.inventory.domain.mappers.ITicketMapper;
import com.flav.cinema_service_inventory.inventory.domain.repository.ITicketRepository;
import com.flav.cinema_service_inventory.inventory.domain.services.ITicketService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class TicketServicesImpl implements ITicketService {

    private final ITicketRepository repository;
    private final ITicketMapper mapper;

    public TicketServicesImpl(ITicketRepository repository, ITicketMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public TicketResponseDTO addStock(TicketRequestDTO ticket) {
        Optional<Ticket> ticketDB = repository.inStock(ticket.getIdMovie());

        if(ticketDB.isPresent()) {
            ticketDB.get().setNumberOfTickets(ticketDB.get().getNumberOfTickets() + ticket.getNumberOfTickets());

            log.info(String.format("Successful request in class | TicketServicesImpl | addStock | movie id %s, amount %s ",
                    ticket.getIdMovie() ,ticket.getNumberOfTickets()));

            return mapper.toResponseDTO(repository.addStock(ticketDB.get()));
        }

        Ticket request = mapper.toEntity(ticket);

        log.info(String.format("Successful request in class | TicketServicesImpl | addStock | create movie id %s, amount %s ",
                ticket.getIdMovie() ,ticket.getNumberOfTickets()));

        return mapper.toResponseDTO(repository.addStock(request));
    }

    @Override
    public TicketResponseDTO inStock(int idMovie) {
        Ticket ticketDB = repository.inStock(idMovie).orElseThrow(() -> {
            log.info(String.format("Request error in class | TicketServicesImpl | inStock | movie id %s not found",
                    idMovie));

            return new TicketMovieNotFound(
                    String.format(TicketConstants.MOVIE_NOT_FOUND, idMovie), HttpStatus.NOT_FOUND);
        });

        if(ticketDB.getNumberOfTickets() < 0) {
            log.info(String.format("Request error in class | TicketServicesImpl | inStock | movie ticket %s ",
                    ticketDB.getNumberOfTickets()));

            throw new NotTicketAvailable(
                    String.format(TicketConstants.NOT_TICKETS_AVAILABLE, idMovie), HttpStatus.BAD_REQUEST);
        }

        log.info(String.format("Successful request in class | TicketServicesImpl | inStock | movie id %s",
                idMovie));

        return mapper.toResponseDTO(ticketDB);
    }

    @Override
    public List<TicketResponseDTO> showStock() {
        log.info("Successful request in class | TicketServicesImpl | showStock");
        return repository.showStock()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    public TicketResponseDTO takeOutStock(int idMovie) {
        TicketResponseDTO ticketDB = this.inStock(idMovie);

        ticketDB.setNumberOfTickets(ticketDB.getNumberOfTickets() - 1);

        Ticket request = mapper.ResponseDTOtoEntity(ticketDB);

        log.info(String.format("Successful request in class | TicketServicesImpl | takeOutStock | movie id %s, amount 1 ",
                idMovie));

        return mapper.toResponseDTO(repository.addStock(request));
    }
}
