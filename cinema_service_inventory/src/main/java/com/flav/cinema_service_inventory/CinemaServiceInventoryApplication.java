package com.flav.cinema_service_inventory;

import com.flav.cinema_service_inventory.inventory.domain.entity.Ticket;
import com.flav.cinema_service_inventory.inventory.persistence.repository.ITicketRepositoryJpa;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class CinemaServiceInventoryApplication implements CommandLineRunner {

	private final ITicketRepositoryJpa repositoryJpa;

	public CinemaServiceInventoryApplication(ITicketRepositoryJpa repositoryJpa) {
		this.repositoryJpa = repositoryJpa;
	}

	@Override
	public void run(String... args) throws Exception {
		Ticket ticket1 = Ticket.builder().idMovie(1).numberOfTickets(10).build();
		Ticket ticket2 = Ticket.builder().idMovie(2).numberOfTickets(15).build();
		Ticket ticket3 = Ticket.builder().idMovie(4).numberOfTickets(5).build();

		repositoryJpa.saveAll(List.of(ticket1, ticket2, ticket3));
	}

	public static void main(String[] args) {
		SpringApplication.run(CinemaServiceInventoryApplication.class, args);
	}

}
