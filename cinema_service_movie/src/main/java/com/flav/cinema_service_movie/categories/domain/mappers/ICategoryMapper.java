package com.flav.cinema_service_movie.categories.domain.mappers;

import com.flav.cinema_service_movie.categories.domain.dtos.reponse.CategoryResponseDTO;
import com.flav.cinema_service_movie.categories.domain.dtos.request.CategoryRequestDTO;
import com.flav.cinema_service_movie.categories.domain.entity.Category;

public interface ICategoryMapper {

    public Category toEntity(CategoryRequestDTO category);
    public CategoryResponseDTO toResponseDTO(Category category);

}
