package com.flav.cinema_service_movie.categories.domain.repository;

import com.flav.cinema_service_movie.categories.domain.entity.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICategoryRepository {

    public List<Category> findAll();
    public Optional<Category> findOne(UUID id);
    public Category create(Category category);
    public Optional<Category> findByName(String name);

}
