package com.flav.cinema_service_inventory.inventory.presentation.controllers.advanc_exceptions_controller;

import com.flav.cinema_service_inventory.inventory.domain.dtos.reponse.ErrorFieldsDTO;
import com.flav.cinema_service_inventory.inventory.domain.exceptions.NotTicketAvailable;
import com.flav.cinema_service_inventory.inventory.domain.exceptions.TicketMovieNotFound;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerExceptions {

    @ExceptionHandler(TicketMovieNotFound.class)
    public ResponseEntity<String> handleEmptyInput(TicketMovieNotFound emptyInputException){
        return new ResponseEntity<>(emptyInputException.getMessage(), emptyInputException.getStatus());
    }

    @ExceptionHandler(NotTicketAvailable.class)
    public ResponseEntity<String> handleEmptyInput(NotTicketAvailable emptyInputException){
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
