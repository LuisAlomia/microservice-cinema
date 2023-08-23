package com.flav.cinema_service_movie.categories.presentation.rest_controllers;

import com.flav.cinema_service_movie.categories.domain.dtos.reponse.CategoryResponseDTO;
import com.flav.cinema_service_movie.categories.domain.dtos.request.CategoryRequestDTO;
import com.flav.cinema_service_movie.categories.domain.services.ICategoryServices;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class CategoryControllers {

    private final ICategoryServices services;

    public CategoryControllers(ICategoryServices services) {
        this.services = services;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAll() {
        return ResponseEntity.status(200).body(services.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getOne(@PathVariable("id") Long id) {
        return ResponseEntity.status(200).body(services.findOne(id));
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@Valid @RequestBody CategoryRequestDTO category) {
        return ResponseEntity.status(201).body(services.create(category));
    }

}
