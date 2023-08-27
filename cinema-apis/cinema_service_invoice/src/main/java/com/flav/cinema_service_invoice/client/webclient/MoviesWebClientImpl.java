package com.flav.cinema_service_invoice.client.webclient;

import com.flav.cinema_service_invoice.client.dtos.MovieResponseDTO;
import com.flav.cinema_service_invoice.invoice.domain.exceptions.ClientError;
import com.flav.cinema_service_invoice.invoice.domain.services.IMovieClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class MoviesWebClientImpl implements IMovieClient {

    private final WebClient.Builder webClientBuilder;

    public MoviesWebClientImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public ResponseEntity<List<MovieResponseDTO>> getAllMovieById(List<Long> idMovies) {
        return webClientBuilder.build()
                        .post()
                        .uri("lb://cinema-service-movie/cinema/api/v1/movies/list")
                        .bodyValue(idMovies)
                        .retrieve()
                        .onStatus(HttpStatusCode::isError, clientResponse ->
                                Mono.error(new ClientError("Client movie api Internal server error",
                                        HttpStatus.INTERNAL_SERVER_ERROR)))
                        .bodyToMono(ResponseEntity.class)
                        .block();
    }

    @Override
    public ResponseEntity<MovieResponseDTO> getOneMovie(Long id) {
        return webClientBuilder.build()
                        .get()
                        .uri(String.format("lb://cinema-service-movie/cinema/api/v1/movies/%s", id))
                        .retrieve()
                        .onStatus(HttpStatusCode::isError, clientResponse ->
                                Mono.error(new ClientError("Client movie api Internal server error",
                                        HttpStatus.INTERNAL_SERVER_ERROR)))
                        .bodyToMono(ResponseEntity.class)
                        .block();
    }
}

