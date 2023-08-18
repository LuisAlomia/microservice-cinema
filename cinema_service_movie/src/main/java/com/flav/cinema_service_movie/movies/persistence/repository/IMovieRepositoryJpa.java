package com.flav.cinema_service_movie.movies.persistence.repository;

import com.flav.cinema_service_movie.movies.domain.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IMovieRepositoryJpa extends JpaRepository<Movie, Long> {

    public List<Movie> findByCategoryId(Long idCategory);
    public Optional<Movie> findByTitle(String title);

}
