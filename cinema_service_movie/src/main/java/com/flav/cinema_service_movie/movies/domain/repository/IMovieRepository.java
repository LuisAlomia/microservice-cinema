package com.flav.cinema_service_movie.movies.domain.repository;

import com.flav.cinema_service_movie.movies.domain.entity.Movie;

import java.util.List;
import java.util.Optional;

public interface IMovieRepository {

    public List<Movie> findAll();
    public Optional<Movie> findOne(Long id);
    public List<Movie> findCategory(Long idCategory);
    public Movie create(Movie category);
    public Optional<Movie> findByTitle(String title);

}
