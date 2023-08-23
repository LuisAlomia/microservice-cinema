package com.flav.cinema_service_movie.categories.domain.dtos.reponse;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponseDTO {

    private Long id;
    private String name;

}
