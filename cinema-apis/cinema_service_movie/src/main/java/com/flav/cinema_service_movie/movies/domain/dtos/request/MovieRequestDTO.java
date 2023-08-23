package com.flav.cinema_service_movie.movies.domain.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieRequestDTO {

    @NotNull
    @NotEmpty(message = "Title is mandatory")
    private String title;

    @NotNull
    @NotEmpty(message = "description is mandatory")
    private String description;

    @NotNull
    @NotEmpty(message = "image is mandatory")
    private String image;

    @NotNull
    @NotEmpty(message = "video is mandatory")
    private String video;

    @NotNull(message = "release date is mandatory")
    private Date releaseDate;

    @NotNull
    @Min(value = 5, message = "must have at least 5 USD")
    private double price;

    @NotNull
    private Long category;

    @Min(value = 1, message = "numberOfTickets must be greater than 0")
    private int numberOfTickets;

}
