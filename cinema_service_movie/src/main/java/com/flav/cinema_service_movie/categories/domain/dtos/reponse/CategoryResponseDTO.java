package com.flav.cinema_service_movie.categories.domain.dtos.reponse;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponseDTO {

    private UUID id;
    private String name;

}
