package com.flav.cinema_service_movie.movies.domain.dtos.reponse;

import com.flav.cinema_service_movie.categories.domain.entity.Category;
import com.flav.cinema_service_movie.client.dtos.TicketResponseDTO;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String image;
    private String video;
    private Date releaseDate;
    private double price;
    private Category category;
    private TicketResponseDTO tickets;

}
