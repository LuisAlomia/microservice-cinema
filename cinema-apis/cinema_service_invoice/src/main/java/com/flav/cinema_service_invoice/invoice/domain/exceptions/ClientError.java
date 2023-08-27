package com.flav.cinema_service_invoice.invoice.domain.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@RequiredArgsConstructor
public class ClientError extends RuntimeException {

    private final String message;
    private final HttpStatus status;

}
