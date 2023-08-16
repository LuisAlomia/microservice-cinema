package com.flav.cinema_service_movie.categories.domain.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@RequiredArgsConstructor
public class CategoryResourceExists extends RuntimeException{

    private final String message;
    private final HttpStatus status;

}
