package com.flav.cinema_service_movie;

import com.flav.cinema_service_movie.categories.domain.entity.Category;
import com.flav.cinema_service_movie.categories.persistence.repository.ICategoryRepositoryJpa;
import com.flav.cinema_service_movie.movies.domain.entity.Movie;
import com.flav.cinema_service_movie.movies.persistence.repository.IMovieRepositoryJpa;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class CinemaServiceMovieApplication implements CommandLineRunner {

	private final ICategoryRepositoryJpa categoryRepositoryJpa;
	private final IMovieRepositoryJpa movieRepositoryJpa;

	public CinemaServiceMovieApplication(ICategoryRepositoryJpa categoryRepositoryJpa, IMovieRepositoryJpa movieRepositoryJpa) {
		this.categoryRepositoryJpa = categoryRepositoryJpa;
		this.movieRepositoryJpa = movieRepositoryJpa;
	}

	@Override
	public void run(String... args) throws Exception {
			Category category1 = Category.builder().name("terror").build();
			Category category2 = Category.builder().name("anime").build();
			Category category3 = Category.builder().name("comics").build();

		if (categoryRepositoryJpa.findAll().isEmpty()) {
			categoryRepositoryJpa.saveAll(List.of(category1, category2, category3));
		}

			Movie movie1 = Movie.builder().title("one piece").description("movie one piece").image("http//image.movie").video("http//video.movie").category(category2).build();
			Movie movie2 = Movie.builder().title("dragon ball").description("movie dragon ball").image("http//image.movie1").video("http//video.movie1").category(category2).build();
			Movie movie3 = Movie.builder().title("just lig").description("movie just lig").image("http//image.movie2").video("http//video.movie2").category(category3).build();

		if (movieRepositoryJpa.findAll().isEmpty()) {
			movieRepositoryJpa.saveAll(List.of(movie1, movie2, movie3));
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(CinemaServiceMovieApplication.class, args);
	}

}
