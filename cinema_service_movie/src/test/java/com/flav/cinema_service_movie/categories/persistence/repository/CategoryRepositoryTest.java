package com.flav.cinema_service_movie.categories.persistence.repository;

import com.flav.cinema_service_movie.categories.domain.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private ICategoryRepositoryJpa repositoryJpa;

    @BeforeEach
    void init() {
        Category category = Category.builder().name("test").build();
    }

    @DisplayName("Should show all categories")
    @Test
    void findAll() {
        //Given

        //When
        List<Category> result = repositoryJpa.findAll();

        //Then
        assertEquals(3, result.size());
        assertTrue(result.get(1).getName().contains("anime"));
    }

    @DisplayName("Should found category by id")
    @Test
    void findOne() {
        //Given
        Category category = Category.builder().name("test").build();
        Category newCategory = repositoryJpa.save(category);

        //When
        Optional<Category> result = repositoryJpa.findById(newCategory.getId());

        //Then
        assertTrue(result.isPresent());
        assertEquals("test", result.get().getName());
    }

    @DisplayName("Should create a new category in database")
    @Test
    void create() {
        //Given
        Category category = Category.builder().name("test").build();

        //When
        Category result = repositoryJpa.save(category);

        //Then
        assertNotNull(result);
        assertEquals("test", result.getName());
    }

    @DisplayName("Should found category by name")
    @Test
    void findByName() {
        //Given

        //When
        Optional<Category> result = repositoryJpa.findByName("anime");

        //Then
        assertTrue(result.isPresent());
        assertEquals("anime", result.get().getName());
    }
}