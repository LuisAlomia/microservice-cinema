package com.flav.cinema_service_invoice.client.dtos;

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
    private Date releaseDate;
    private double price;
    private String video;
    private CategoryResposeDTO category;
    private TicketResponseDTO tickets;

}
