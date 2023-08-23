package com.flav.cinema_service_movie.client.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketResponseDTO {

    private Long id;
    private int idMovie;
    private int numberOfTickets;

}
