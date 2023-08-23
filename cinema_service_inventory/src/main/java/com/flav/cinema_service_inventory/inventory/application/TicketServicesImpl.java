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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            return mapper.toResponseDTO(repository.addStock(ticketDB.get()));
        }

        Ticket request = mapper.toEntity(ticket);

        return mapper.toResponseDTO(repository.addStock(request));
    }

    @Override
    public TicketResponseDTO inStock(int idMovie) {
        Ticket ticketDB = repository.inStock(idMovie).orElseThrow(() ->
            new TicketMovieNotFound(
                    String.format(TicketConstants.MOVIE_NOT_FOUND, idMovie), HttpStatus.NOT_FOUND));

        if(ticketDB.getNumberOfTickets() < 0) {
            throw new NotTicketAvailable(
                    String.format(TicketConstants.NOT_TICKETS_AVAILABLE, idMovie), HttpStatus.BAD_REQUEST);
        }

        return mapper.toResponseDTO(ticketDB);
    }

    @Override
    public List<TicketResponseDTO> showStock() {
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

        return mapper.toResponseDTO(repository.addStock(request));
    }
}
