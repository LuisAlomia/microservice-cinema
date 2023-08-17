package com.flav.cinema_service_movie;

import com.flav.cinema_service_movie.categories.domain.entity.Category;
import com.flav.cinema_service_movie.categories.persistence.repository.ICategoryRepositoryJpa;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class CinemaServiceMovieApplication implements CommandLineRunner {

	private final ICategoryRepositoryJpa repositoryJpa;

	public CinemaServiceMovieApplication(ICategoryRepositoryJpa repositoryJpa) {
		this.repositoryJpa = repositoryJpa;
	}

	@Override
	public void run(String... args) throws Exception {
		Category category1 = Category.builder().id(UUID.randomUUID()).name("terror").build();
		Category category2 = Category.builder().id(UUID.randomUUID()).name("miedo").build();
		Category category3 = Category.builder().id(UUID.randomUUID()).name("accion").build();

		repositoryJpa.saveAll(List.of(category1, category2, category3));
	}

	public static void main(String[] args) {
		SpringApplication.run(CinemaServiceMovieApplication.class, args);
	}

}
