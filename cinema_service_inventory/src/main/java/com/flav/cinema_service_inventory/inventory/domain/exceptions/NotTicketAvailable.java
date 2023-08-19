package com.flav.cinema_service_inventory.inventory.domain.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class NotTicketAvailable extends RuntimeException {
    private final String message;
    private final HttpStatus status;

    public NotTicketAvailable(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
