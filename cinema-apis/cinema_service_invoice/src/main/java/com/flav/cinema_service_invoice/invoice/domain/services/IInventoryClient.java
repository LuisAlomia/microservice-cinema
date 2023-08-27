package com.flav.cinema_service_invoice.invoice.domain.services;

import com.flav.cinema_service_invoice.client.dtos.TicketResponseDTO;

public interface IInventoryClient {

    public TicketResponseDTO inStock(int idMovie);
    public TicketResponseDTO takeOutStock(int idMovie);

}
