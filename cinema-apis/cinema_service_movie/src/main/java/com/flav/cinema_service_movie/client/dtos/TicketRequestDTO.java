package com.flav.cinema_service_movie.client.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketRequestDTO {

    private int idMovie;
    private int numberOfTickets;

}
