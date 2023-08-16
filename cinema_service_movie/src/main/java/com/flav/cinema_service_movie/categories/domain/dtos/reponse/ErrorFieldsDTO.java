package com.flav.cinema_service_movie.categories.domain.dtos.reponse;

import lombok.*;
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
