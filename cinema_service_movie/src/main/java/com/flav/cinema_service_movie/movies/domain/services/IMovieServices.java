package com.flav.cinema_service_movie.movies.domain.services;

import com.flav.cinema_service_movie.movies.domain.dtos.reponse.MovieResponseDTO;
import com.flav.cinema_service_movie.movies.domain.dtos.request.MovieRequestDTO;

import java.util.List;

public interface IMovieServices {

    public List<MovieResponseDTO> findAll();
    public MovieResponseDTO findOne(Long id);
    public List<MovieResponseDTO> findCategory(Long idCategory);
    public MovieResponseDTO create(MovieRequestDTO category);

}
