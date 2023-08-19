package com.flav.cinema_service_movie.movies.domain.dtos.reponse;

import com.flav.cinema_service_movie.categories.domain.entity.Category;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String image;
    private String video;
    private Category category;

}
