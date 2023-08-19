package com.flav.cinema_service_inventory.inventory.domain.services;

import com.flav.cinema_service_inventory.inventory.domain.dtos.reponse.TicketResponseDTO;
import com.flav.cinema_service_inventory.inventory.domain.dtos.request.TicketRequestDTO;

import java.util.List;

public interface ITicketService {

    public TicketResponseDTO addStock(TicketRequestDTO ticket);
    public TicketResponseDTO inStock(int idMovie);
    public List<TicketResponseDTO> showStock();
    public TicketResponseDTO takeOutStock(int idMovie);

}
