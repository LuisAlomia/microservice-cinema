package com.flav.cinema_service_invoice.invoice.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Temporal(TemporalType.DATE)
    @Column(name = "create_at")
    private Date createAt;

    private int amount;
    private double total;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice")
    private List<InvoiceItem> invoiceItem = new ArrayList<>();

}
