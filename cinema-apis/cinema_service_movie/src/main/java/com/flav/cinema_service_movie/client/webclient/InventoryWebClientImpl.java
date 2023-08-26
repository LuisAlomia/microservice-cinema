package com.flav.cinema_service_movie.client.webclient;

import com.flav.cinema_service_movie.client.dtos.TicketRequestDTO;
import com.flav.cinema_service_movie.client.dtos.TicketResponseDTO;
import com.flav.cinema_service_movie.movies.domain.exceptions.ClientError;
import com.flav.cinema_service_movie.movies.domain.services.IInventoryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class InventoryWebClientImpl implements IInventoryClient {

    private final WebClient.Builder webClientBuilder;

    public InventoryWebClientImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public TicketResponseDTO addTicket(TicketRequestDTO ticket) {
        return webClientBuilder.build()
                        .post()
                        .uri("lb://cinema-service-inventory/cinema/api/v1/inventory")
                        .bodyValue(ticket)
                        .retrieve()
                        .onStatus(HttpStatusCode::isError, clientResponse ->
                                Mono.error(new ClientError("Client inventory api Internal server error",
                                        HttpStatus.INTERNAL_SERVER_ERROR)))
                        .bodyToMono(TicketResponseDTO.class)
                        .block();
    }

    @Override
    public TicketResponseDTO inStock(Long idMovie) {
        return webClientBuilder.build()
                        .get()
                        .uri(String.format("lb://cinema-service-inventory/cinema/api/v1/inventory/%s", idMovie))
                        .retrieve()
                        .onStatus(HttpStatusCode::isError, clientResponse ->
                                Mono.error(new ClientError("Client inventory api Internal server error",
                                        HttpStatus.INTERNAL_SERVER_ERROR)))
                        .bodyToMono(TicketResponseDTO.class)
                        .block();
    }
}

