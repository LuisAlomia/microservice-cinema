package com.flav.cinema_service_movie.movies.application;

import com.flav.cinema_service_movie.categories.domain.constants.CategoryConstants;
import com.flav.cinema_service_movie.categories.domain.entity.Category;
import com.flav.cinema_service_movie.categories.domain.exceptions.CategoryNotFound;
import com.flav.cinema_service_movie.categories.domain.repository.ICategoryRepository;
import com.flav.cinema_service_movie.movies.domain.dtos.reponse.MovieResponseDTO;
import com.flav.cinema_service_movie.movies.domain.dtos.request.MovieRequestDTO;
import com.flav.cinema_service_movie.movies.domain.constants.MovieConstants;
import com.flav.cinema_service_movie.movies.domain.entity.Movie;
import com.flav.cinema_service_movie.movies.domain.exceptions.MovieNotFound;
import com.flav.cinema_service_movie.movies.domain.exceptions.MovieResourceExists;
import com.flav.cinema_service_movie.movies.domain.mappers.IMovieMapper;
import com.flav.cinema_service_movie.movies.domain.repository.IMovieRepository;
import com.flav.cinema_service_movie.movies.domain.services.IMovieServices;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServicesImpl implements IMovieServices {

    private final IMovieRepository repository;
    private final ICategoryRepository categoryRepository;
    private final IMovieMapper mapper;

    public MovieServicesImpl(IMovieRepository repository, ICategoryRepository categoryRepository, IMovieMapper mapper) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public List<MovieResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    public MovieResponseDTO findOne(Long id) {
        Optional<Movie> movieDB = repository.findOne(id);

        if(movieDB.isEmpty()) {
            throw new MovieNotFound(
                    String.format(MovieConstants.RESOURCE_WITH_ID_NOT_FOUND, id),
                    HttpStatus.NOT_FOUND
            );
        }

        return mapper.toResponseDTO(movieDB.get());
    }

    @Override
    public List<MovieResponseDTO> findCategory(Long idCategory) {
        return repository.findCategory(idCategory)
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    public MovieResponseDTO create(MovieRequestDTO movie) {
        Optional<Category> categoryDB = categoryRepository.findOne(movie.getCategory());

        if(categoryDB.isEmpty()) {
           throw new CategoryNotFound(
                   String.format(CategoryConstants.RESOURCE_WITH_ID_NOT_FOUND, movie.getCategory()),
                   HttpStatus.NOT_FOUND);
        }

        Optional<Movie> movieDB = repository.findByTitle(movie.getTitle().toLowerCase());

        if(movieDB.isPresent()) {
            throw new MovieResourceExists(
                    String.format(MovieConstants.RESOURCE_WITH_NAME_EXISTS, movieDB.get().getTitle())
                    ,HttpStatus.BAD_REQUEST);
        };

        Movie movieSave = Movie
                .builder()
                .title(movie.getTitle())
                .description(movie.getDescription())
                .image(movie.getImage())
                .video(movie.getVideo())
                .category(categoryDB.get())
                .build();

        return mapper.toResponseDTO(repository.create(movieSave));
    }
}
