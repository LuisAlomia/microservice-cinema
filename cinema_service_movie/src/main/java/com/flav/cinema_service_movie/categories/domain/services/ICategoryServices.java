package com.flav.cinema_service_movie.categories.domain.services;

import com.flav.cinema_service_movie.categories.domain.dtos.reponse.CategoryResponseDTO;
import com.flav.cinema_service_movie.categories.domain.dtos.request.CategoryRequestDTO;

import java.util.List;

public interface ICategoryServices {

    public List<CategoryResponseDTO> findAll();
    public CategoryResponseDTO findOne(Long id);
    public CategoryResponseDTO create(CategoryRequestDTO category);

}
