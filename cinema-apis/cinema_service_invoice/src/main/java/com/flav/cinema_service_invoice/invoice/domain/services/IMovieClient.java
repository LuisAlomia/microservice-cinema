package com.flav.cinema_service_invoice.invoice.domain.services;

import com.flav.cinema_service_invoice.client.dtos.MovieResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IMovieClient {

    public ResponseEntity<MovieResponseDTO> getOneMovie(Long id);
    public ResponseEntity<List<MovieResponseDTO>> getAllMovieById(List<Long> idMovies);

}
