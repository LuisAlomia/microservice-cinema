package com.flav.cinema_service_inventory.inventory.persistence.repository;

import com.flav.cinema_service_inventory.inventory.domain.entity.Ticket;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:/config-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TicketRepositoryTest {

    @Autowired
    private ITicketRepositoryJpa repository;

    Ticket ticket1 = Ticket.builder().idMovie(5).numberOfTickets(10).build();

    @Order(2)
    @DisplayName("Should add a movie in the inventory")
    @Test
    void addStock() {
        //Given

        //When
        Ticket result = repository.save(ticket1);

        //Then
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(5, result.getIdMovie());
        assertEquals(10, result.getNumberOfTickets());
    }

    @DisplayName("Should show a movies in stock")
    @Test
    void inStock() {
        //Given

        //When
        Optional<Ticket> result = repository.findByIdMovie(1);

        //Then
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getNumberOfTickets());
        assertEquals(1, result.get().getIdMovie());
    }

    @Order(1)
    @DisplayName("Should show the movies in stock")
    @Test
    void showStock() {
        //Given

        //When
        List<Ticket> result = repository.findAll();

        //Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(1, result.get(0).getNumberOfTickets());
    }

}