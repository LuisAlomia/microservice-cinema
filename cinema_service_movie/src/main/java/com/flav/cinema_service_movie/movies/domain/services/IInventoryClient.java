package com.flav.cinema_service_movie.movies.domain.services;

import com.flav.cinema_service_movie.client.dtos.TicketRequestDTO;
import com.flav.cinema_service_movie.client.dtos.TicketResponseDTO;

public interface IInventoryClient {

    public TicketResponseDTO addTicket(TicketRequestDTO ticket);
    public TicketResponseDTO inStock(Long idMovie);

}
