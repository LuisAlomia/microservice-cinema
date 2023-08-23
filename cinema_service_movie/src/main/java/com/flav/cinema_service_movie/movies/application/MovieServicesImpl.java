package com.flav.cinema_service_movie.movies.application;

import com.flav.cinema_service_movie.categories.domain.constants.CategoryConstants;
import com.flav.cinema_service_movie.categories.domain.entity.Category;
import com.flav.cinema_service_movie.categories.domain.exceptions.CategoryNotFound;
import com.flav.cinema_service_movie.categories.domain.repository.ICategoryRepository;
import com.flav.cinema_service_movie.client.dtos.TicketRequestDTO;
import com.flav.cinema_service_movie.client.dtos.TicketResponseDTO;
import com.flav.cinema_service_movie.movies.domain.dtos.reponse.MovieResponseDTO;
import com.flav.cinema_service_movie.movies.domain.dtos.request.MovieRequestDTO;
import com.flav.cinema_service_movie.movies.domain.constants.MovieConstants;
import com.flav.cinema_service_movie.movies.domain.entity.Movie;
import com.flav.cinema_service_movie.movies.domain.exceptions.MovieNotFound;
import com.flav.cinema_service_movie.movies.domain.exceptions.MovieResourceExists;
import com.flav.cinema_service_movie.movies.domain.mappers.IMovieMapper;
import com.flav.cinema_service_movie.movies.domain.repository.IMovieRepository;
import com.flav.cinema_service_movie.movies.domain.services.IInventoryClient;
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
    private final IInventoryClient inventoryClient;

    public MovieServicesImpl(IMovieRepository repository, ICategoryRepository categoryRepository,
                             IMovieMapper mapper, IInventoryClient inventoryClient) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
        this.inventoryClient = inventoryClient;
    }

    @Override
    public List<MovieResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(movie -> {
                    //makes a call to the inventory api to get the number of tickets
                    var stock = inventoryClient.inStock(movie.getId());

                    //add the tickets to each movie
                    movie.setTickets(stock);
                    return movie;
                })
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    public MovieResponseDTO findOne(Long id) {
        //validates if the movie exists otherwise returns an excess
        Movie movieDB = repository.findOne(id).orElseThrow(() -> new MovieNotFound(
                String.format(MovieConstants.RESOURCE_WITH_ID_NOT_FOUND, id),
                HttpStatus.NOT_FOUND
        ));

        //makes a call to the inventory api to get the number of tickets
        TicketResponseDTO stock = inventoryClient.inStock(id);

        //add the tickets to movie
        movieDB.setTickets(stock);

        return mapper.toResponseDTO(movieDB);
    }

    @Override
    public List<MovieResponseDTO> findCategory(Long idCategory) {
        return repository.findCategory(idCategory)
                .stream()
                .map(movie -> {
                    //makes a call to the inventory api to get the number of tickets
                    var stock = inventoryClient.inStock(movie.getId());

                    //add the tickets to each movie
                    movie.setTickets(stock);
                    return movie;
                })
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<MovieResponseDTO> findAllById(List<Long> idMovies) {
        return repository.findAllById(idMovies)
                .stream()
                .map(movie -> {
                    //makes a call to the inventory api to get the number of tickets
                    var stock = inventoryClient.inStock(movie.getId());

                    //add the tickets to each movie
                    movie.setTickets(stock);
                    return movie;
                })
                .map(mapper::toResponseDTO)
                .toList();
    };

    @Override
    public MovieResponseDTO create(MovieRequestDTO movie) {
        Optional<Movie> movieDB = repository.findByTitle(movie.getTitle().toLowerCase());

        if (movieDB.isPresent()) {
            throw new MovieResourceExists(String.format(MovieConstants.RESOURCE_WITH_NAME_EXISTS, movie.getTitle())
                    ,HttpStatus.BAD_REQUEST);
        }

        //validates if the movie exists otherwise returns an excess
        Category categoryDB = categoryRepository.findOne(movie.getCategory()).orElseThrow(() ->
                new CategoryNotFound(String.format(CategoryConstants.RESOURCE_WITH_ID_NOT_FOUND, movie.getCategory()),
                HttpStatus.NOT_FOUND));

        Movie newMovie = Movie
                .builder()
                .title(movie.getTitle())
                .description(movie.getDescription())
                .image(movie.getImage())
                .video(movie.getVideo())
                .releaseDate(movie.getReleaseDate())
                .price(movie.getPrice())
                .category(categoryDB)
                .build();

        //add new movie in database
        Movie saveMovie = repository.create(newMovie);

        TicketRequestDTO addInventory = TicketRequestDTO
                                            .builder()
                                            .idMovie(Math.toIntExact(saveMovie.getId()))
                                            .numberOfTickets(movie.getNumberOfTickets())
                                            .build();

        //add to inventory the movie created
        inventoryClient.addTicket(addInventory);

        return mapper.toResponseDTO(saveMovie);
    }
}
