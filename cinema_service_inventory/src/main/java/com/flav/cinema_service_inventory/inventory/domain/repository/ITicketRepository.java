package com.flav.cinema_service_inventory.inventory.domain.repository;

import com.flav.cinema_service_inventory.inventory.domain.entity.Ticket;

import java.util.List;
import java.util.Optional;

public interface ITicketRepository {

    public Ticket addStock(Ticket ticket);
    public Optional<Ticket> inStock(int idMovie);
    public List<Ticket> showStock();
    public Ticket takeOutStock(int idMovie);

}
