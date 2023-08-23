package com.flav.cinema_service_invoice.invoice.domain.dtos.reponse;

import com.flav.cinema_service_invoice.client.dtos.CategoryResposeDTO;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceMovieResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String image;
    private Date releaseDate;
    private double price;
    private String video;
    private CategoryResposeDTO category;

}
