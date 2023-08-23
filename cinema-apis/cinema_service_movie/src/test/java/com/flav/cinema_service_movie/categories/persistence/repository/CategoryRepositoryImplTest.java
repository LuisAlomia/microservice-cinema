package com.flav.cinema_service_movie.categories.persistence.repository;

import com.flav.cinema_service_movie.categories.domain.entity.Category;
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
class CategoryRepositoryImplTest {

    @Mock
    private ICategoryRepositoryJpa mockRepositoryJpa;

    @InjectMocks
    private CategoryRepositoryImpl repository;

    private final Category category1 = Category.builder().id(1L).name("terror").build();
    private final Category category2 = Category.builder().id(2L).name("anime").build();
    private final Category category3 = Category.builder().id(3L).name("comics").build();

    @DisplayName("Should show all categories")
    @Test
    void findAll() {
        //Given
        List<Category> list = List.of(category1, category2, category3);

        BDDMockito.given(mockRepositoryJpa.findAll()).willReturn(list);

        //When
        List<Category> result = repository.findAll();

        //Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("terror", result.get(0).getName());
    }

    @DisplayName("Should find a category by id")
    @Test
    void findOne() {
        //Given
        BDDMockito.given(mockRepositoryJpa.findById(category1.getId())).willReturn(Optional.of(category1));

        //When
        Optional<Category> result = repository.findOne(category1.getId());

        //Then
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals("terror", result.get().getName());
    }

    @DisplayName("Should create a new category in database")
    @Test
    void create() {
        //Given
        BDDMockito.given(mockRepositoryJpa.save(category1)).willReturn(category1);

        //When
        Category result = repository.create(category1);

        //Then
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("terror", result.getName());
    }

    @DisplayName("Should find a category by name")
    @Test
    void findByName() {
        //Given
        BDDMockito.given(mockRepositoryJpa.findByName(category1.getName())).willReturn(Optional.of(category1));

        //When
        Optional<Category> result = repository.findByName(category1.getName());

        //Then
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals("terror", result.get().getName());
    }
}