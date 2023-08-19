package com.flav.cinema_service_inventory.inventory.domain.dtos.reponse;

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
