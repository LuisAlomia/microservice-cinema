package com.flav.cinema_service_invoice.invoice.domain.dtos.reponse;

import com.flav.cinema_service_invoice.client.dtos.MovieResponseDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceItemResponseDTO {

    private Long id;
    private Long idMovie;
    private InvoiceMovieResponseDTO movie;

}
