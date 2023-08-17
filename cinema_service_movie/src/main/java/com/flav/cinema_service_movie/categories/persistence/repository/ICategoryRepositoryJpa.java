package com.flav.cinema_service_movie.categories.persistence.repository;

import com.flav.cinema_service_movie.categories.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategoryRepositoryJpa extends JpaRepository<Category, Long> {

    public Optional<Category> findByName(String name);

}
