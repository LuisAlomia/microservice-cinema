package com.flav.cinema_service_movie.movies.persistence.repository;

import com.flav.cinema_service_movie.categories.domain.entity.Category;
import com.flav.cinema_service_movie.movies.domain.entity.Movie;
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
class MovieRepositoryImplTest {

    @Mock
    private IMovieRepositoryJpa mockRepo;

    @InjectMocks
    private MovieRepositoryImpl repository;

    Category category2 = Category.builder().id(2L).name("anime").build();
    Category category3 = Category.builder().id(3L).name("comics").build();
    Movie movie1 = Movie.builder().id(1L).title("one piece").description("movie one piece").image("http//image.movie").video("http//video.movie").category(category2).build();
    Movie movie2 = Movie.builder().id(2L).title("dragon ball").description("movie dragon ball").image("http//image.movie1").video("http//video.movie1").category(category2).build();
    Movie movie3 = Movie.builder().id(3L).title("just lig").description("movie just lig").image("http//image.movie2").video("http//video.movie2").category(category3).build();

    @DisplayName("Should return a list of movies")
    @Test
    void findAll() {
        //Given
        BDDMockito.given(mockRepo.findAll()).willReturn(List.of(movie1, movie2, movie3));

        //When
        List<Movie> result = repository.findAll();

        //Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("one piece", result.get(0).getTitle());
    }

    @DisplayName("Should find a movie by id")
    @Test
    void findOne() {
        //Given
        BDDMockito.given(mockRepo.findById(1L)).willReturn(Optional.of(movie1));

        //When
        Optional<Movie> result = repository.findOne(1L);

        //Then
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals("one piece", result.get().getTitle());
        assertEquals(1, result.get().getId());
    }

    @DisplayName("Should return a list movie by category")
    @Test
    void findCategory() {
        //Given
        BDDMockito.given(mockRepo.findByCategoryId(2L)).willReturn(List.of(movie1, movie2));

        //When
        List<Movie> result = repository.findCategory(2L);

        //Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("one piece", result.get(0).getTitle());
    }

    @DisplayName("Should add a movie in database")
    @Test
    void create() {
        //Given
        BDDMockito.given(mockRepo.save(movie1)).willReturn(movie1);

        //When
        Movie result = repository.create(movie1);

        //Then
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("one piece", result.getTitle());
    }

    @DisplayName("Should find a movie by title")
    @Test
    void findByTitle() {
        //Given
        BDDMockito.given(mockRepo.findByTitle("one piece")).willReturn(Optional.of(movie1));

        //When
        Optional<Movie> result = repository.findByTitle("one piece");

        //Then
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals("one piece", result.get().getTitle());
        assertEquals(1, result.get().getId());
    }
}