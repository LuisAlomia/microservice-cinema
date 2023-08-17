package com.flav.cinema_service_movie.categories.application;

import com.flav.cinema_service_movie.categories.domain.constants.CategoryConstants;
import com.flav.cinema_service_movie.categories.domain.dtos.reponse.CategoryResponseDTO;
import com.flav.cinema_service_movie.categories.domain.dtos.request.CategoryRequestDTO;
import com.flav.cinema_service_movie.categories.domain.entity.Category;
import com.flav.cinema_service_movie.categories.domain.exceptions.CategoryNotFound;
import com.flav.cinema_service_movie.categories.domain.exceptions.CategoryResourceExists;
import com.flav.cinema_service_movie.categories.domain.mappers.ICategoryMapper;
import com.flav.cinema_service_movie.categories.domain.repository.ICategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryServicesImplTest {

    @Mock
    private ICategoryRepository mockRepo;

    @Mock
    private ICategoryMapper mockMapper;

    @InjectMocks
    private CategoryServicesImpl services;

    private final Category category1 = Category.builder().id(1L).name("terror").build();
    private final Category category2 = Category.builder().id(2L).name("anime").build();
    private final Category category3 = Category.builder().id(3L).name("comics").build();
    private final CategoryRequestDTO request = CategoryRequestDTO.builder().name("terror").build();

    @DisplayName("Should return a list of categories")
    @Test
    void findAll() {
        //Given
        var categoryList = List.of(category1, category2, category3);

        BDDMockito.given(mockRepo.findAll()).willReturn(categoryList);

        for(Category item: categoryList) {
            BDDMockito
                    .given(mockMapper.toResponseDTO(item))
                    .willReturn(CategoryResponseDTO
                            .builder()
                            .id(item.getId())
                            .name(item.getName())
                            .build());
        }

        //When
        List<CategoryResponseDTO> result = services.findAll();

        //Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("terror", result.get(0).getName());

    }

    @DisplayName("Should return a category")
    @Test
    void findOne() {
        //Given
        BDDMockito.given(mockRepo.findOne(category1.getId())).willReturn(Optional.of(category1));

        BDDMockito.given(mockMapper.toResponseDTO(category1))
                .willReturn(CategoryResponseDTO
                        .builder()
                        .id(category1.getId())
                        .name(category1.getName())
                        .build());

        //Where
        CategoryResponseDTO result = services.findOne(category1.getId());

        //Then
        assertNotNull(result);
        assertEquals(category1.getId(), result.getId());
        assertEquals("terror",result.getName());
    }

    @DisplayName("Should throw new exception CategoryNotFound")
    @Test
    void categoryNotFound() {
        //Given
        Long id = 1L;
        BDDMockito.given(mockRepo.findOne(id)).willReturn(Optional.empty());

        //When
        CategoryNotFound result = assertThrowsExactly(CategoryNotFound.class, () -> {
            services.findOne(id);
        });

        //Then
        assertEquals(String.format(CategoryConstants.RESOURCE_WITH_ID_NOT_FOUND, id), result.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatus());
    }

    @DisplayName("Should save a category in database")
    @Test
    void create() {
        //Given
        BDDMockito.given(mockMapper.toEntity(request)).willReturn(category1);

        BDDMockito.given(mockRepo.create(category1)).willReturn(category1);

        BDDMockito.given(mockMapper.toResponseDTO(category1))
                .willReturn(CategoryResponseDTO
                        .builder()
                        .id(category1.getId())
                        .name(category1.getName())
                        .build());

        //When
        CategoryResponseDTO result = services.create(request);

        //Then
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("terror", result.getName());
    }

    @DisplayName("Should throw new exception CategoryResourceExists")
    @Test
    void categoryResourceExists() {
        //Given
        BDDMockito.given(mockRepo.findByName(category1.getName())).willReturn(Optional.of(category1));

        //When
        CategoryResourceExists result = assertThrowsExactly(CategoryResourceExists.class, () -> {
            services.create(request);
        });

        //Then
        assertEquals(String.format(CategoryConstants.RESOURCE_WITH_NAME_EXISTS, category1.getName()), result.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatus());
    }

}