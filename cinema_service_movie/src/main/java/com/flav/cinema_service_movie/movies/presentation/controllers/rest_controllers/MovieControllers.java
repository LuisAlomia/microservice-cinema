package com.flav.cinema_service_movie.movies.presentation.controllers.rest_controllers;

import com.flav.cinema_service_movie.movies.domain.dtos.reponse.MovieResponseDTO;
import com.flav.cinema_service_movie.movies.domain.dtos.request.MovieRequestDTO;
import com.flav.cinema_service_movie.movies.domain.services.IMovieServices;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieControllers {

    private final IMovieServices services;

    public MovieControllers(IMovieServices services) {
        this.services = services;
    }

    @GetMapping
    public ResponseEntity<List<MovieResponseDTO>> getAll() {
        return ResponseEntity.status(200).body(services.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> getOne(@PathVariable("id") Long id) {
        return ResponseEntity.status(200).body(services.findOne(id));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<MovieResponseDTO>> getMovieByCategory(@PathVariable("id") Long id) {
        return ResponseEntity.status(200).body(services.findCategory(id));
    }

    @PostMapping
    public ResponseEntity<MovieResponseDTO> create(@Valid @RequestBody MovieRequestDTO movie) {
        return ResponseEntity.status(201).body(services.create(movie));
    }

}
