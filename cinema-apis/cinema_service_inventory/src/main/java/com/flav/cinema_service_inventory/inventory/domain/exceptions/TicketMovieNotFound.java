package com.flav.cinema_service_inventory.inventory.domain.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class TicketMovieNotFound extends RuntimeException {

    private final String message;
    private final HttpStatus status;

    public TicketMovieNotFound(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
