package com.flav.cinema_service_movie.movies.domain.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

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


    @NotNull
    private Long category;

}
