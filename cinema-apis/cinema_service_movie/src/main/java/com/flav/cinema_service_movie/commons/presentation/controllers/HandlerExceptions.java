package com.flav.cinema_service_movie.commons.presentation.controllers;

import com.flav.cinema_service_movie.categories.domain.exceptions.CategoryNotFound;
import com.flav.cinema_service_movie.categories.domain.exceptions.CategoryResourceExists;
import com.flav.cinema_service_movie.commons.domain.dtos.ErrorFieldsDTO;
import com.flav.cinema_service_movie.movies.domain.exceptions.ClientError;
import com.flav.cinema_service_movie.movies.domain.exceptions.MovieNotFound;
import com.flav.cinema_service_movie.movies.domain.exceptions.MovieResourceExists;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerExceptions {

    @ExceptionHandler(CategoryNotFound.class)
    public ResponseEntity<String> handleEmptyInput(CategoryNotFound emptyInputException){
        return ResponseEntity.status(emptyInputException.getStatus()).body(emptyInputException.getMessage());
    }

    @ExceptionHandler(CategoryResourceExists.class)
    public ResponseEntity<String> handleEmptyInput(CategoryResourceExists emptyInputException){
        return ResponseEntity.status(emptyInputException.getStatus()).body(emptyInputException.getMessage());
    }

    @ExceptionHandler(MovieNotFound.class)
    public ResponseEntity<String> handleEmptyInput(MovieNotFound emptyInputException){
        return new ResponseEntity<>(emptyInputException.getMessage(), emptyInputException.getStatus());
    }

    @ExceptionHandler(MovieResourceExists.class)
    public ResponseEntity<String> handleEmptyInput(MovieResourceExists emptyInputException){
        return new ResponseEntity<>(emptyInputException.getMessage(), emptyInputException.getStatus());
    }

    @ExceptionHandler(ClientError.class)
    public ResponseEntity<String> handleEmptyInput(ClientError emptyInputException){
        return new ResponseEntity<>(emptyInputException.getMessage(), emptyInputException.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorFieldsDTO> handleException(MethodArgumentNotValidException ex) {
        ErrorFieldsDTO dto = ErrorFieldsDTO.builder()
                .status(HttpStatus.BAD_REQUEST)
                .error("Validation error")
                .type("Field error")
                .detailedMessages(ex
                        .getBindingResult()
                        .getAllErrors()
                        .stream()
                        .map(err -> err.unwrap(ConstraintViolation.class))
                        .map(err -> String.format("'%s' %s", err.getPropertyPath(), err.getMessage()))
                        .toList()
                )
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
    }

}
