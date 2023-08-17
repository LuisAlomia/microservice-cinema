package com.flav.cinema_service_movie.categories.domain.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequestDTO {

    @NotEmpty(message = "Name is mandatory")
    @Size(min = 4, message = "The field name cannot be empty and must be greater than 3 characters")
    private String name;

}
