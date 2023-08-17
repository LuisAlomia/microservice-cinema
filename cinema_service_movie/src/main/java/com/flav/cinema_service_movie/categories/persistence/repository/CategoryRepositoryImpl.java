package com.flav.cinema_service_movie.categories.persistence.repository;

import com.flav.cinema_service_movie.categories.domain.entity.Category;
import com.flav.cinema_service_movie.categories.domain.repository.ICategoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepositoryImpl implements ICategoryRepository {

    private final ICategoryRepositoryJpa repositoryJpa;

    public CategoryRepositoryImpl(ICategoryRepositoryJpa repositoryJpa) {
        this.repositoryJpa = repositoryJpa;
    }

    @Override
    public List<Category> findAll() {
        return repositoryJpa.findAll();
    }

    @Override
    public Optional<Category> findOne(Long id) {
        return repositoryJpa.findById(id);
    }

    @Override
    public Category create(Category category) {
        return repositoryJpa.save(category);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return repositoryJpa.findByName(name);
    }

}
