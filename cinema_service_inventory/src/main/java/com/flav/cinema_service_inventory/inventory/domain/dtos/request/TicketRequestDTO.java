package com.flav.cinema_service_inventory.inventory.domain.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketRequestDTO {

    @NotNull(message = "idMovie is mandatory")
    @Min(value = 1, message = "idMovie must be greater than 0")
    private int idMovie;

    @NotNull(message = "numberOfTickets is mandatory")
    @Min(value = 1, message = "numberOfTickets must be greater than 0")
    private int numberOfTickets;

}
