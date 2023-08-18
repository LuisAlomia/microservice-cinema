package com.flav.cinema_service_movie.movies.domain.mappers;

import com.flav.cinema_service_movie.movies.domain.dtos.reponse.MovieResponseDTO;
import com.flav.cinema_service_movie.movies.domain.entity.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper implements IMovieMapper{

    @Override
    public MovieResponseDTO toResponseDTO(Movie movie) {
        return MovieResponseDTO
                .builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .image(movie.getImage())
                .video(movie.getVideo())
                .category(movie.getCategory())
                .build();
    }
}
