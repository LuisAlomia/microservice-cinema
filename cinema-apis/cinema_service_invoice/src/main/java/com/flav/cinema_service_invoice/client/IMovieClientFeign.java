package com.flav.cinema_service_invoice.client;

import com.flav.cinema_service_invoice.client.dtos.MovieResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "cinema-service-movie")
public interface IMovieClientFeign {

    @GetMapping("/cinema/api/v1/movies/{id}")
    public ResponseEntity<MovieResponseDTO> getOneMovie(@PathVariable("id") Long id);

    @PostMapping("/cinema/api/v1/movies/list")
    public ResponseEntity<List<MovieResponseDTO>> getAllMovieById(@RequestBody List<Long> idMovies);

}
