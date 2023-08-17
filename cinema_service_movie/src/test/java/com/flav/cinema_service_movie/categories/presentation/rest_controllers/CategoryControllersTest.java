package com.flav.cinema_service_movie.categories.presentation.rest_controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flav.cinema_service_movie.categories.domain.constants.CategoryConstants;
import com.flav.cinema_service_movie.categories.domain.dtos.request.CategoryRequestDTO;
import com.flav.cinema_service_movie.categories.domain.exceptions.CategoryNotFound;
import com.flav.cinema_service_movie.categories.domain.exceptions.CategoryResourceExists;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:/config-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryControllersTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Order(1)
    @DisplayName("Should return a list of categories")
    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/")
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$.[0].name").value("terror"))
                .andExpect(jsonPath("$.[1].name").value("anime"));
    }

    @Order(2)
    @DisplayName("Should return a category")
    @Test
    void getOne() throws Exception {
        mockMvc.perform(get("/{id}", 1).accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("terror"));
    }

    @DisplayName("Should throw new exception CategoryNotFound")
    @Test
    void categoryNotFound() throws Exception {
        Long id = 100L;

        mockMvc.perform(get("/{id}", id)
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CategoryNotFound))
                .andExpect(result -> assertEquals(
                        String.format(CategoryConstants.RESOURCE_WITH_ID_NOT_FOUND, id),
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Order(3)
    @DisplayName("Should add a new category in database")
    @Test
    void create() throws Exception {
        CategoryRequestDTO request = CategoryRequestDTO.builder().name("comedia").build();

        String response = mockMvc
                .perform(post("/")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("comedia"))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @DisplayName("Should throw new exception CategoryResourceExists")
    @Test
    void categoryResourceExists() throws Exception {
        CategoryRequestDTO request = CategoryRequestDTO.builder().name("anime").build();

        mockMvc.perform(post("/")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CategoryResourceExists))
                .andExpect(result -> assertEquals(
                        String.format(CategoryConstants.RESOURCE_WITH_NAME_EXISTS, request.getName()),
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }
}