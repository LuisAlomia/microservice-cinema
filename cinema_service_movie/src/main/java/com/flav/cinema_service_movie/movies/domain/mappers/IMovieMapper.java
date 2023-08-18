package com.flav.cinema_service_movie.movies.domain.mappers;


import com.flav.cinema_service_movie.movies.domain.dtos.reponse.MovieResponseDTO;
import com.flav.cinema_service_movie.movies.domain.entity.Movie;

public interface IMovieMapper {

    public MovieResponseDTO toResponseDTO(Movie movie);

}
