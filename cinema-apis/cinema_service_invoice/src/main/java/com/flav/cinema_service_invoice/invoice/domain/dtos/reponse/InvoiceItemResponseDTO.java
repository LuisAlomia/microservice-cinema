package com.flav.cinema_service_invoice.invoice.domain.dtos.reponse;

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
