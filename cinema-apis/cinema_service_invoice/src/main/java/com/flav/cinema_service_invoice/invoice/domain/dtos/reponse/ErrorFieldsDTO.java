package com.flav.cinema_service_invoice.invoice.domain.dtos.reponse;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@Builder
public class ErrorFieldsDTO {

    private final HttpStatus status;
    private final String error;
    private final String type;
    private List<String> detailedMessages;

    public ErrorFieldsDTO(HttpStatus status, String error, String type, List<String> detailedMessages) {
        this.status = status;
        this.error = error;
        this.type = type;
        this.detailedMessages = detailedMessages;
    }
}
