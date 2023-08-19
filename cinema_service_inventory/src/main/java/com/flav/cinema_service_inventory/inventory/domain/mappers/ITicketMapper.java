package com.flav.cinema_service_inventory.inventory.domain.mappers;

import com.flav.cinema_service_inventory.inventory.domain.dtos.reponse.TicketResponseDTO;
import com.flav.cinema_service_inventory.inventory.domain.dtos.request.TicketRequestDTO;
import com.flav.cinema_service_inventory.inventory.domain.entity.Ticket;

public interface ITicketMapper {

    public Ticket toEntity(TicketRequestDTO ticket);
    public TicketResponseDTO toResponseDTO(Ticket ticket);
    public Ticket ResponseDTOtoEntity(TicketResponseDTO ticket);

}
