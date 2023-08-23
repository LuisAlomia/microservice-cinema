package com.flav.cinema_service_movie.movies.application;

import com.flav.cinema_service_movie.categories.domain.constants.CategoryConstants;
import com.flav.cinema_service_movie.categories.domain.entity.Category;
import com.flav.cinema_service_movie.categories.domain.exceptions.CategoryNotFound;
import com.flav.cinema_service_movie.categories.domain.repository.ICategoryRepository;
import com.flav.cinema_service_movie.client.dtos.TicketResponseDTO;
import com.flav.cinema_service_movie.movies.domain.constants.MovieConstants;
import com.flav.cinema_service_movie.movies.domain.dtos.reponse.MovieResponseDTO;
import com.flav.cinema_service_movie.movies.domain.dtos.request.MovieRequestDTO;
import com.flav.cinema_service_movie.movies.domain.entity.Movie;
import com.flav.cinema_service_movie.movies.domain.exceptions.MovieNotFound;
import com.flav.cinema_service_movie.movies.domain.exceptions.MovieResourceExists;
import com.flav.cinema_service_movie.movies.domain.mappers.IMovieMapper;
import com.flav.cinema_service_movie.movies.domain.repository.IMovieRepository;
import com.flav.cinema_service_movie.movies.domain.services.IInventoryClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MovieServicesImplTest {

    @Mock
    private IMovieRepository mockRepoMovie;

    @Mock
    private ICategoryRepository mockRepoCategory;

    @Mock
    private IMovieMapper mapper;

    @Mock
    private IInventoryClient mockInventoryClient;

    @InjectMocks
    private MovieServicesImpl services;

    Category category2 = Category.builder().id(2L).name("anime").build();
    Category category3 = Category.builder().id(3L).name("comics").build();
    Movie movie1 = Movie.builder().id(1L).title("one piece").description("movie one piece").image("http//image.movie").video("http//video.movie").category(category2).build();
    Movie movie2 = Movie.builder().id(2L).title("dragon ball").description("movie dragon ball").image("http//image.movie1").video("http//video.movie1").category(category2).build();
    Movie movie3 = Movie.builder().id(3L).title("just lig").description("movie just lig").image("http//image.movie2").video("http//video.movie2").category(category3).build();
    MovieRequestDTO request = MovieRequestDTO.builder().title("one piece").description("movie one piece").image("http//image.movie").video("http//video.movie").category(2L).build();

    @DisplayName("Should return a list of movies")
    @Test
    void findAll() {
        //Given
        var list = List.of(movie1, movie2, movie3);

        BDDMockito.given(mockRepoMovie.findAll()).willReturn(list);

        for(Movie item: list) {
            BDDMockito.given(mockInventoryClient.inStock(item.getId())).willReturn(TicketResponseDTO
                    .builder()
                    .id(item.getId())
                    .idMovie(Math.toIntExact(item.getId()))
                    .numberOfTickets(10)
                    .build());
        }

        for(Movie item: list) {
            BDDMockito.given(mapper.toResponseDTO(item))
                    .willReturn(MovieResponseDTO.builder().id(item.getId()).title(item.getTitle())
                            .description(item.getDescription()).image(item.getImage()).video(item.getVideo())
                            .category(item.getCategory()).build());
        }

        //When
        List<MovieResponseDTO> result = services.findAll();

        //Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("one piece", result.get(0).getTitle());
    }

    @DisplayName("Should find a movie by id")
    @Test
    void findOne() {
        //Given
        BDDMockito.given(mockRepoMovie.findOne(1L)).willReturn(Optional.of(movie1));

        BDDMockito.given(mockInventoryClient.inStock(1L)).willReturn(TicketResponseDTO
                .builder()
                .id(1L)
                .idMovie(Math.toIntExact(movie1.getId()))
                .numberOfTickets(10)
                .build());

        BDDMockito.given(mapper.toResponseDTO(movie1))
                    .willReturn(MovieResponseDTO.builder().id(movie1.getId()).title(movie1.getTitle())
                            .description(movie1.getDescription()).image(movie1.getImage()).video(movie1.getVideo())
                            .category(movie1.getCategory()).build());

        //When
        MovieResponseDTO result = services.findOne(1L);

        //Then
        assertNotNull(result);
        assertEquals("one piece", result.getTitle());
        assertEquals(1, result.getId());
    }

    @DisplayName("Should throw new exception Movie not found")
    @Test
    void movie_not_found() {
        //Given
        BDDMockito.given(mockRepoMovie.findOne(1L)).willReturn(Optional.empty());

        //When
        MovieNotFound result = assertThrowsExactly(MovieNotFound.class, () -> {
            services.findOne(1L);
        });

        //Then
        assertEquals(String.format(MovieConstants.RESOURCE_WITH_ID_NOT_FOUND, 1L) , result.getMessage());
    }

    @DisplayName("Should return a list movie by category")
    @Test
    void findCategory() {
        //Given
        var list = List.of(movie1, movie2);

        BDDMockito.given(mockRepoMovie.findCategory(2L)).willReturn(list);

        for(Movie item: list) {
            BDDMockito.given(mockInventoryClient.inStock(item.getId())).willReturn(TicketResponseDTO
                    .builder()
                    .id(item.getId())
                    .idMovie(Math.toIntExact(item.getId()))
                    .numberOfTickets(10)
                    .build());
        }

        for(Movie item: list) {
            BDDMockito.given(mapper.toResponseDTO(item))
                    .willReturn(MovieResponseDTO.builder().id(item.getId()).title(item.getTitle())
                            .description(item.getDescription()).image(item.getImage()).video(item.getVideo())
                            .category(item.getCategory()).build());
        }

        //When
        List<MovieResponseDTO> result = services.findCategory(2L);

        //Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("one piece", result.get(0).getTitle());
    }

    @DisplayName("Should throw new exception category not found")
    @Test
    void category_not_found() {
        //Given
        BDDMockito.given(mockRepoCategory.findOne(request.getCategory())).willReturn(Optional.empty());

        //When
        CategoryNotFound result = assertThrowsExactly(CategoryNotFound.class, () -> {
            services.create(request);
        });

        //Then
        assertEquals(String.format(CategoryConstants.RESOURCE_WITH_ID_NOT_FOUND, request.getCategory()),
                result.getMessage());
    }

    @DisplayName("Should throw new exception category not found")
    @Test
    void title_movie_exits() {
        //Given
        BDDMockito.given(mockRepoMovie.findByTitle(movie1.getTitle())).willReturn(Optional.of(movie1));

        //When
        MovieResourceExists result = assertThrowsExactly(MovieResourceExists.class, () -> {
            services.create(request);
        });

        //Then
        assertEquals(String.format(MovieConstants.RESOURCE_WITH_NAME_EXISTS, movie1.getTitle()),
                result.getMessage());
    }
}