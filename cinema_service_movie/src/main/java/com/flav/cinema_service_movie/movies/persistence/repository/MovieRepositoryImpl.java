package com.flav.cinema_service_movie.movies.persistence.repository;

import com.flav.cinema_service_movie.movies.domain.entity.Movie;
import com.flav.cinema_service_movie.movies.domain.repository.IMovieRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MovieRepositoryImpl implements IMovieRepository {

    private final IMovieRepositoryJpa repositoryJpa;

    public MovieRepositoryImpl(IMovieRepositoryJpa repositoryJpa) {
        this.repositoryJpa = repositoryJpa;
    }

    @Override
    public List<Movie> findAll() {
        return repositoryJpa.findAll();
    }

    @Override
    public Optional<Movie> findOne(Long id) {
        return repositoryJpa.findById(id);
    }

    @Override
    public List<Movie> findCategory(Long idCategory) {
        return repositoryJpa.findByCategoryId(idCategory);
    }

    @Override
    public Movie create(Movie category) {
        return repositoryJpa.save(category);
    }

    @Override
    public Optional<Movie> findByTitle(String name) {
        return repositoryJpa.findByTitle(name);
    }
}
