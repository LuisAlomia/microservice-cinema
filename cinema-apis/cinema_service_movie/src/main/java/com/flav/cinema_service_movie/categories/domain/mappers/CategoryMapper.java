package com.flav.cinema_service_movie.categories.domain.mappers;

import com.flav.cinema_service_movie.categories.domain.dtos.reponse.CategoryResponseDTO;
import com.flav.cinema_service_movie.categories.domain.dtos.request.CategoryRequestDTO;
import com.flav.cinema_service_movie.categories.domain.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper implements ICategoryMapper {

    @Override
    public Category toEntity(CategoryRequestDTO category) {
        return Category.builder()
                .name(category.getName().trim().toLowerCase())
                .build();
    }

    @Override
    public CategoryResponseDTO toResponseDTO(Category category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

}
