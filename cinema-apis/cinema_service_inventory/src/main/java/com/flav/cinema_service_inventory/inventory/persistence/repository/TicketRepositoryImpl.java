package com.flav.cinema_service_inventory.inventory.persistence.repository;

import com.flav.cinema_service_inventory.inventory.domain.entity.Ticket;
import com.flav.cinema_service_inventory.inventory.domain.repository.ITicketRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TicketRepositoryImpl implements ITicketRepository {

    private final ITicketRepositoryJpa repositoryJpa;

    public TicketRepositoryImpl(ITicketRepositoryJpa repositoryJpa) {
        this.repositoryJpa = repositoryJpa;
    }

    @Override
    public Ticket addStock(Ticket ticket) {
        return repositoryJpa.save(ticket);
    }

    @Override
    public Optional<Ticket> inStock(int idMovie) {
        return repositoryJpa.findByIdMovie(idMovie);
    }

    @Override
    public List<Ticket> showStock() {
        return repositoryJpa.findAll();
    }

}
