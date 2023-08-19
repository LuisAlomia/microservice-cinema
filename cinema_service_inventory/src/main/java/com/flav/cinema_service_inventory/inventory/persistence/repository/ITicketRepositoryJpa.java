package com.flav.cinema_service_inventory.inventory.persistence.repository;

import com.flav.cinema_service_inventory.inventory.domain.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITicketRepositoryJpa extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByIdMovie(int idMovie);

}
