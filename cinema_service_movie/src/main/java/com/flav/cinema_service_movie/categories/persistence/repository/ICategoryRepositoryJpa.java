package com.flav.cinema_service_movie.categories.persistence.repository;

import com.flav.cinema_service_movie.categories.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ICategoryRepositoryJpa extends JpaRepository<Category, UUID> {

    public Optional<Category> findByName(String name);

}
