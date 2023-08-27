package com.flav.cinema_service_movie.categories.application;

import com.flav.cinema_service_movie.categories.domain.constants.CategoryConstants;
import com.flav.cinema_service_movie.categories.domain.dtos.reponse.CategoryResponseDTO;
import com.flav.cinema_service_movie.categories.domain.dtos.request.CategoryRequestDTO;
import com.flav.cinema_service_movie.categories.domain.entity.Category;
import com.flav.cinema_service_movie.categories.domain.exceptions.CategoryNotFound;
import com.flav.cinema_service_movie.categories.domain.exceptions.CategoryResourceExists;
import com.flav.cinema_service_movie.categories.domain.mappers.ICategoryMapper;
import com.flav.cinema_service_movie.categories.domain.repository.ICategoryRepository;
import com.flav.cinema_service_movie.categories.domain.services.ICategoryServices;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class CategoryServicesImpl implements ICategoryServices {

    private final ICategoryRepository repository;
    private final ICategoryMapper mapper;

    public CategoryServicesImpl(ICategoryRepository repository, ICategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<CategoryResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(item -> {
                    log.info("Successful request in class | CategoryServicesImpl | findAll");
                    return mapper.toResponseDTO(item);
                })
                .toList();
    }

    @Override
    public CategoryResponseDTO findOne(Long id) {
        Optional<Category> categoryDB = repository.findOne(id);

        if (categoryDB.isEmpty()) {
            log.error(String.format("Successful request in class | CategoryServicesImpl | findOne | category with id %s not found",
                    id));

            throw new CategoryNotFound(
                    String.format(CategoryConstants.RESOURCE_WITH_ID_NOT_FOUND, id),
                    HttpStatus.NOT_FOUND
            );
        }

        log.info("Successful request in class | CategoryServicesImpl | findOne");
        return mapper.toResponseDTO(categoryDB.get());
    }

    @Override
    public CategoryResponseDTO create(CategoryRequestDTO category) {
        Optional<Category> categoryDB = repository.findByName(category.getName().trim());

        if (categoryDB.isPresent()) {
            log.error(String.format("Successful request in class | CategoryServicesImpl | create | category name %s exist",
                    category.getName()));

            throw new CategoryResourceExists(
                    String.format(CategoryConstants.RESOURCE_WITH_NAME_EXISTS, categoryDB.get().getName()),
                    HttpStatus.BAD_REQUEST
            );
        }

        log.info("Successful request in class | CategoryServicesImpl | create");
        return mapper.toResponseDTO(repository.create(mapper.toEntity(category)));
    }
}
