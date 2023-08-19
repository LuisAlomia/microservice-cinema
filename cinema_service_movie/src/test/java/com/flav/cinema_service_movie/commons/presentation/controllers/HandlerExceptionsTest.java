package com.flav.cinema_service_movie.commons.presentation.controllers;

import com.flav.cinema_service_movie.movies.domain.dtos.request.MovieRequestDTO;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandlerExceptionsTest {

    private Validator validator;

    MovieRequestDTO movie = MovieRequestDTO.builder().build();

    @BeforeEach
    void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void handleException() {
        //Given

        //When
        var violation = validator.validate(movie);

        for(var item: violation.stream().distinct().toList()) {
            System.out.println(item.getPropertyPath());
        }

        //Then
        assertFalse(violation.isEmpty());
        assertEquals(9, violation.size());
    }
}