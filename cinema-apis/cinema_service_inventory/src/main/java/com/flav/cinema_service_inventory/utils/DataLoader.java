package com.flav.cinema_service_inventory.utils;

import com.flav.cinema_service_inventory.inventory.domain.entity.Ticket;
import com.flav.cinema_service_inventory.inventory.persistence.repository.ITicketRepositoryJpa;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final ITicketRepositoryJpa repositoryJpa;

    public DataLoader(ITicketRepositoryJpa repositoryJpa) {
        this.repositoryJpa = repositoryJpa;
    }

    @Override
    public void run(String... args) throws Exception {
        Ticket ticket1 = Ticket.builder().idMovie(1).numberOfTickets(1).build();
        Ticket ticket2 = Ticket.builder().idMovie(2).numberOfTickets(1).build();
        Ticket ticket3 = Ticket.builder().idMovie(3).numberOfTickets(0).build();

        if(repositoryJpa.findAll().isEmpty()) {
            repositoryJpa.saveAll(List.of(ticket1, ticket2, ticket3));
        }
    }

}
