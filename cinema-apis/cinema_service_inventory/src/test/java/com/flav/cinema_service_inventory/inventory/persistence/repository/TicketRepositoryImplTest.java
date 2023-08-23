package com.flav.cinema_service_inventory.inventory.persistence.repository;

import com.flav.cinema_service_inventory.inventory.domain.entity.Ticket;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TicketRepositoryImplTest {

    @Mock
    private ITicketRepositoryJpa mockRepo;

    @InjectMocks
    private TicketRepositoryImpl repository;

    Ticket ticket1 = Ticket.builder().id(1L).idMovie(1).numberOfTickets(10).build();
    Ticket ticket2 = Ticket.builder().id(2L).idMovie(2).numberOfTickets(15).build();
    Ticket ticket3 = Ticket.builder().id(3L).idMovie(4).numberOfTickets(5).build();

    @DisplayName("Should add a movie in the inventory")
    @Test
    void addStock() {
        //Given
        BDDMockito.given(mockRepo.save(ticket1)).willReturn(ticket1);

        //When
        Ticket result = repository.addStock(ticket1);

        //Then
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(1, result.getIdMovie());
        assertEquals(10, result.getNumberOfTickets());
    }

    @DisplayName("Should show a movies in stock")
    @Test
    void inStock() {
        //Given
        BDDMockito.given(mockRepo.findByIdMovie(1)).willReturn(Optional.of(ticket1));

        //When
        Optional<Ticket> result = repository.inStock(1);

        //Then
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(10, result.get().getNumberOfTickets());
        assertEquals(1, result.get().getIdMovie());
    }

    @DisplayName("Should show the movies in stock")
    @Test
    void showStock() {
        //Given
        BDDMockito.given(mockRepo.findAll()).willReturn(List.of(ticket1, ticket2, ticket3));

        //When
        List<Ticket> result = repository.showStock();

        //Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(10, result.get(0).getNumberOfTickets());
    }

}