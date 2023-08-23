package com.flav.cinema_service_movie.movies.persistence.repository;

import com.flav.cinema_service_movie.categories.domain.entity.Category;
import com.flav.cinema_service_movie.movies.domain.entity.Movie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestPropertySource(locations = "classpath:/config-test.properties")
class MovieRepositoryTest {

    @Autowired
    private IMovieRepositoryJpa repositoryJpa;

    Category category2 = Category.builder().id(2L).name("anime").build();
    Movie movie1 = Movie.builder().id(1L).title("one piece").description("movie one piece").image("http//image.movie").video("http//video.movie").category(category2).build();

    @DisplayName("Should return a list of movies")
    @Test
    void findAll() {
        //Given

        //When
        List<Movie> result = repositoryJpa.findAll();

        //Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("one piece", result.get(0).getTitle());
    }

    @DisplayName("Should find a movie by id")
    @Test
    void findOne() {
        //Given

        //When
        Optional<Movie> result = repositoryJpa.findById(1L);

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

        //When
        List<Movie> result = repositoryJpa.findByCategoryId(2L);

        //Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("one piece", result.get(0).getTitle());
    }

    @DisplayName("Should add a movie in database")
    @Test
    void create() {
        //Given

        //When
        Movie result = repositoryJpa.save(movie1);

        //Then
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("one piece", result.getTitle());
    }

    @DisplayName("Should find a movie by title")
    @Test
    void findByTitle() {
        //Given

        //When
        Optional<Movie> result = repositoryJpa.findByTitle("one piece");

        //Then
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals("one piece", result.get().getTitle());
        assertEquals(1, result.get().getId());
    }
}