package com.flav.cinema_service_movie.movies.domain.entity;

import com.flav.cinema_service_movie.categories.domain.entity.Category;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String image;
    private String video;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category")
    private Category category;

}
