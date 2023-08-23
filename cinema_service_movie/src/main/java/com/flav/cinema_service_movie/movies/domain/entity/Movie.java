package com.flav.cinema_service_movie.movies.domain.entity;

import com.flav.cinema_service_movie.categories.domain.entity.Category;
import com.flav.cinema_service_movie.client.dtos.TicketResponseDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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

    @Temporal(TemporalType.DATE)
    @Column(name = "release_date")
    private Date releaseDate;

    private double price;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category")
    private Category category;

    @Transient
    private TicketResponseDTO tickets;

}
