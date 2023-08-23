package com.flav.cinema_service_invoice.invoice.presentation.controllers.advanc_exceptions_controller;

import com.flav.cinema_service_invoice.invoice.domain.dtos.reponse.ErrorFieldsDTO;
import com.flav.cinema_service_invoice.invoice.domain.exceptions.InvoiceNotFound;
import com.flav.cinema_service_invoice.invoice.domain.exceptions.NotMovieTicket;
import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerExceptions {

    @ExceptionHandler(InvoiceNotFound.class)
    public ResponseEntity<String> handleEmptyInput(InvoiceNotFound emptyInputException){
        return new ResponseEntity<>(emptyInputException.getMessage(), emptyInputException.getStatus());
    }

    @ExceptionHandler(NotMovieTicket.class)
    public ResponseEntity<String> handleEmptyInput(NotMovieTicket emptyInputException){
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
