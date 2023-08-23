package com.flav.cinema_service_invoice.invoice.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class NotMovieTicket extends RuntimeException {

    private String message;
    private HttpStatus status;

}
