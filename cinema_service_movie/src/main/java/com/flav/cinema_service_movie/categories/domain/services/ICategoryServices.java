package com.flav.cinema_service_movie.categories.domain.services;

import com.flav.cinema_service_movie.categories.domain.dtos.reponse.CategoryResponseDTO;
import com.flav.cinema_service_movie.categories.domain.dtos.request.CategoryRequestDTO;

import java.util.List;
import java.util.UUID;

public interface ICategoryServices {

    public List<CategoryResponseDTO> findAll();
    public CategoryResponseDTO findOne(UUID id);
    public CategoryResponseDTO create(CategoryRequestDTO category);

}
