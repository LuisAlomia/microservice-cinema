package com.flav.cinema_service_inventory.inventory.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_movie")
    private int idMovie;

    @Column(name = "number_of_tickets")
    private int numberOfTickets;

}
