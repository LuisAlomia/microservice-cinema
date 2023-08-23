package com.flav.cinema_service_inventory.inventory.application;

import com.flav.cinema_service_inventory.inventory.domain.constants.TicketConstants;
import com.flav.cinema_service_inventory.inventory.domain.dtos.reponse.TicketResponseDTO;
import com.flav.cinema_service_inventory.inventory.domain.entity.Ticket;
import com.flav.cinema_service_inventory.inventory.domain.exceptions.NotTicketAvailable;
import com.flav.cinema_service_inventory.inventory.domain.exceptions.TicketMovieNotFound;
import com.flav.cinema_service_inventory.inventory.domain.mappers.ITicketMapper;
import com.flav.cinema_service_inventory.inventory.domain.repository.ITicketRepository;
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
class TicketServicesImplTest {

    @Mock
    private ITicketRepository mockRepoTicket;

    @Mock
    private ITicketMapper mapper;

    @InjectMocks
    private TicketServicesImpl services;

    Ticket ticket1 = Ticket.builder().id(1L).idMovie(1).numberOfTickets(10).build();
    Ticket ticket2 = Ticket.builder().id(2L).idMovie(2).numberOfTickets(15).build();
    Ticket ticket3 = Ticket.builder().id(3L).idMovie(4).numberOfTickets(5).build();

    @DisplayName("Should add a movie in the inventory")
    @Test
    void addStock() {
    }

    @DisplayName("Should show a movies in stock")
    @Test
    void inStock() {
        //Given
        BDDMockito.given(mockRepoTicket.inStock(1)).willReturn(Optional.of(ticket1));

        BDDMockito.given(mapper.toResponseDTO(ticket1))
                .willReturn(TicketResponseDTO.builder()
                        .id(ticket1.getId())
                        .idMovie(ticket1.getIdMovie())
                        .numberOfTickets(ticket1.getNumberOfTickets())
                        .build());

        //When
        TicketResponseDTO result = services.inStock(1);

        //Then
        assertNotNull(result);
        assertEquals(10, result.getNumberOfTickets());
        assertEquals(1, result.getIdMovie());
    }

    @DisplayName("Should throw new TicketMovieNotFound")
    @Test
    void movieNotFount() {
        //Given
        BDDMockito.given(mockRepoTicket.inStock(1)).willReturn(Optional.empty());

        //When
        TicketMovieNotFound result = assertThrowsExactly(TicketMovieNotFound.class, () -> {
            services.inStock(1);
        });

        //Then
        assertNotNull(result);
        assertEquals(String.format(TicketConstants.MOVIE_NOT_FOUND, 1), result.getMessage());
    }

    @DisplayName("Should throw new NotTicketAvailable")
    @Test
    void NotTicketAvailable() {
        //Given
        Ticket ticket = Ticket.builder().id(1L).idMovie(1).numberOfTickets(-1).build();

        BDDMockito.given(mockRepoTicket.inStock(1)).willReturn(Optional.of(ticket));

        //When
        NotTicketAvailable result = assertThrowsExactly(NotTicketAvailable.class, () -> {
            services.inStock(1);
        });

        //Then
        assertNotNull(result);
        assertEquals(String.format(TicketConstants.NOT_TICKETS_AVAILABLE, 1), result.getMessage());
    }

    @Test
    void showStock() {
        //Given
        var list = List.of(ticket1, ticket2, ticket3);

        BDDMockito.given(mockRepoTicket.showStock()).willReturn(list);

        for(Ticket item: list) {
            BDDMockito.given(mapper.toResponseDTO(item))
                    .willReturn(TicketResponseDTO.builder()
                            .id(item.getId())
                            .idMovie(item.getIdMovie())
                            .numberOfTickets(item.getNumberOfTickets())
                            .build());

        }

        //When
        List<TicketResponseDTO> result = services.showStock();

        //Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(10, result.get(0).getNumberOfTickets());
    }

    @Test
    void takeOutStock() {
    }
}