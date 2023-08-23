package com.flav.cinema_service_invoice.invoice.domain.dtos.reponse;

import com.flav.cinema_service_invoice.invoice.domain.entity.InvoiceItem;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceResponseDTO {

    private Long id;
    private String code;
    private Date createAt;
    private int amount;
    private double total;
    private List<InvoiceItemResponseDTO> invoiceItem = new ArrayList<>();

}
