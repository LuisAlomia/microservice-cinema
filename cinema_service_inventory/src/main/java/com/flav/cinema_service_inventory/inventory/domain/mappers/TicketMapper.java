package com.flav.cinema_service_inventory.inventory.domain.mappers;

import com.flav.cinema_service_inventory.inventory.domain.dtos.reponse.TicketResponseDTO;
import com.flav.cinema_service_inventory.inventory.domain.dtos.request.TicketRequestDTO;
import com.flav.cinema_service_inventory.inventory.domain.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper implements ITicketMapper{

    @Override
    public Ticket toEntity(TicketRequestDTO ticket) {
        return Ticket
                .builder()
                .idMovie(ticket.getIdMovie())
                .numberOfTickets(ticket.getNumberOfTickets())
                .build();
    }

    @Override
    public TicketResponseDTO toResponseDTO(Ticket ticket) {
        return TicketResponseDTO
                .builder()
                .id(ticket.getId())
                .idMovie(ticket.getIdMovie())
                .numberOfTickets(ticket.getNumberOfTickets())
                .build();
    }

    @Override
    public Ticket ResponseDTOtoEntity(TicketResponseDTO ticket) {
        return Ticket
                .builder()
                .id(ticket.getId())
                .idMovie(ticket.getIdMovie())
                .numberOfTickets(ticket.getNumberOfTickets())
                .build();
    }

}
