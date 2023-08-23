package com.flav.cinema_service_inventory.inventory.presentation.controllers.rest_controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flav.cinema_service_inventory.inventory.domain.constants.TicketConstants;
import com.flav.cinema_service_inventory.inventory.domain.dtos.request.TicketRequestDTO;
import com.flav.cinema_service_inventory.inventory.domain.exceptions.TicketMovieNotFound;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:/config-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TicketControllersTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Order(1)
    @Test
    void showTicket() throws Exception {
        mockMvc.perform(get("/inventory")
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$.[0].idMovie").value(1))
                .andExpect(jsonPath("$.[1].numberOfTickets").value(1));
    }


    @Test
    void TicketMovieNotFound() throws Exception {
        mockMvc.perform(get("/inventory/{idMovie}", 100)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TicketMovieNotFound))
                .andExpect(result -> assertEquals(
                        String.format(TicketConstants.MOVIE_NOT_FOUND, 100),
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Order(2)
    @Test
    void inStock() throws Exception {
        mockMvc.perform(get("/inventory/{idMovie}", 1)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.idMovie").value(1))
                .andExpect(jsonPath("$.numberOfTickets").value(1));
    }

    @Order(3)
    @Test
    void addNumberOfTicket() throws Exception {
        TicketRequestDTO request = TicketRequestDTO.builder().idMovie(2).numberOfTickets(5).build();

        mockMvc.perform(post("/inventory")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.idMovie").value(2))
                .andExpect(jsonPath("$.numberOfTickets").value(6))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void addTicket() throws Exception {
        TicketRequestDTO request = TicketRequestDTO.builder().idMovie(8).numberOfTickets(5).build();

        mockMvc.perform(post("/inventory")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.idMovie").value(8))
                .andExpect(jsonPath("$.numberOfTickets").value(5))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void takeOutStock() throws Exception {
        mockMvc.perform(get("/inventory/take-out-stock/{idMovie}", 1)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.idMovie").value(1))
                .andExpect(jsonPath("$.numberOfTickets").value(0));
    }

    @DisplayName("Should throw new exception fields invalid")
    @Test
    void movieFieldInvalid() throws Exception {
        TicketRequestDTO request = TicketRequestDTO.builder().build();

        mockMvc.perform(post("/inventory")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(jsonPath("$.error").value("Validation error"))
                .andExpect(jsonPath("$.detailedMessages").isArray())
                .andExpect(jsonPath("$.detailedMessages", Matchers.hasSize(2)));

    }
}