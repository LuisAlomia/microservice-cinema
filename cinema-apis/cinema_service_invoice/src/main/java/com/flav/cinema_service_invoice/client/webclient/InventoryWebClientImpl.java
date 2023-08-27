package com.flav.cinema_service_invoice.client.webclient;

import com.flav.cinema_service_invoice.client.dtos.TicketResponseDTO;
import com.flav.cinema_service_invoice.invoice.domain.exceptions.ClientError;
import com.flav.cinema_service_invoice.invoice.domain.services.IInventoryClient;
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
    public TicketResponseDTO takeOutStock(int idMovie) {
        return webClientBuilder.build()
                        .post()
                        .uri(String.format("lb://cinema-service-inventory/cinema/api/v1/inventory/%s", idMovie))
                        .retrieve()
                        .onStatus(HttpStatusCode::isError, clientResponse ->
                                Mono.error(new ClientError("Client inventory api Internal server error",
                                        HttpStatus.INTERNAL_SERVER_ERROR)))
                        .bodyToMono(TicketResponseDTO.class)
                        .block();
    }

    @Override
    public TicketResponseDTO inStock(int idMovie) {
        return webClientBuilder.build()
                        .get()
                        .uri(String.format("lb://cinema-service-inventory/cinema/api/v1/inventory/take-out-stock/%s", idMovie))
                        .retrieve()
                        .onStatus(HttpStatusCode::isError, clientResponse ->
                                Mono.error(new ClientError("Client inventory api Internal server error",
                                        HttpStatus.INTERNAL_SERVER_ERROR)))
                        .bodyToMono(TicketResponseDTO.class)
                        .block();
    }
}

