package com.flav.cinema_service_invoice.invoice.domain.entity;

import com.flav.cinema_service_invoice.client.dtos.MovieResponseDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "invoice_items")
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_movie")
    private Long idMovie;

    @Transient
    private MovieResponseDTO movie;

}
