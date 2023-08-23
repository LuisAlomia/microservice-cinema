package com.flav.cinema_service_movie.movies.presentation.controllers.rest_controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flav.cinema_service_movie.categories.domain.constants.CategoryConstants;
import com.flav.cinema_service_movie.categories.domain.exceptions.CategoryNotFound;
import com.flav.cinema_service_movie.client.dtos.TicketResponseDTO;
import com.flav.cinema_service_movie.movies.domain.constants.MovieConstants;
import com.flav.cinema_service_movie.movies.domain.dtos.request.MovieRequestDTO;
import com.flav.cinema_service_movie.movies.domain.exceptions.MovieNotFound;
import com.flav.cinema_service_movie.movies.domain.exceptions.MovieResourceExists;
import com.flav.cinema_service_movie.movies.domain.services.IInventoryClient;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:/config-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovieControllersTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IInventoryClient mockInventoryClient;

    ObjectMapper objectMapper = new ObjectMapper();


    @Order(1)
    @DisplayName("Should return a list of movies")
    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/movies")
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$.[0].title").value("one piece"))
                .andExpect(jsonPath("$.[1].title").value("dragon ball"));
    }

    @Order(2)
    @DisplayName("Should return a movie")
    @Test
    void getOne() throws Exception {
        mockMvc.perform(get("/movies/{id}", 1).accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("one piece"));
    }

    @DisplayName("Should throw new exception MovieNotFound")
    @Test
    void movieNotFound() throws Exception {
        Long id = 100L;

        mockMvc.perform(get("/movies/{id}", id)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MovieNotFound))
                .andExpect(result -> assertEquals(
                        String.format(MovieConstants.RESOURCE_WITH_ID_NOT_FOUND, id),
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Order(3)
    @DisplayName("Should add a new movie in database")
    @Test
    void create() throws Exception {
        MovieRequestDTO request = MovieRequestDTO.builder().title("jack 2").description("movie jack 2")
                .image("http//image.movie").video("http//video.movie").releaseDate(new Date()).price(5.0)
                .category(3L).numberOfTickets(10).build();

        String response = mockMvc
                .perform(post("/movies")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("jack 2"))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @DisplayName("Should throw new exception MovieResourceExists")
    @Test
    void movieResourceExists() throws Exception {
        MovieRequestDTO request = MovieRequestDTO.builder().title("one piece").description("movie one piece")
                .image("http//image.movie").video("http//video.movie").releaseDate(new Date()).price(10)
                .category(3L).numberOfTickets(10).build();

        mockMvc.perform(post("/movies")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MovieResourceExists))
                .andExpect(result -> assertEquals(
                        String.format(CategoryConstants.RESOURCE_WITH_NAME_EXISTS, request.getTitle()),
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @DisplayName("Should throw new exception CategoryNotFound")
    @Test
    void categoryNotFound() throws Exception {
        MovieRequestDTO request = MovieRequestDTO.builder().title("Example").description("movie Example")
                .image("http//image.movie").video("http//video.movie").releaseDate(new Date())
                .price(8.0).category(100L).numberOfTickets(10).build();

        mockMvc.perform(post("/movies")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CategoryNotFound))
                .andExpect(result -> assertEquals(
                        String.format(CategoryConstants.RESOURCE_WITH_ID_NOT_FOUND, request.getCategory()),
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    void getMovieByCategory() throws Exception {
        Long id = 2L;

        mockMvc.perform(get("/movies/category/{id}", id)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.[0].title").value("one piece"))
                .andExpect(jsonPath("$.[1].title").value("dragon ball"));
    }

    @DisplayName("Should throw new exception fields invalid")
    @Test
    void movieFieldInvalid() throws Exception {
        MovieRequestDTO request = MovieRequestDTO.builder().build();

        mockMvc.perform(post("/movies")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(jsonPath("$.error").value("Validation error"))
                .andExpect(jsonPath("$.detailedMessages").isArray())
                .andExpect(jsonPath("$.detailedMessages", Matchers.hasSize(12)));

    }

    @DisplayName("Should return a list of movie for id")
    @Test
    void findAllById() throws Exception {
        List<Long> request = List.of(1L, 2L, 3L);

        BDDMockito.given(mockInventoryClient.inStock(1L))
                .willReturn(TicketResponseDTO.builder().id(1L).idMovie(1).numberOfTickets(10).build());

        String response = mockMvc
                .perform(post("/movies/list")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}