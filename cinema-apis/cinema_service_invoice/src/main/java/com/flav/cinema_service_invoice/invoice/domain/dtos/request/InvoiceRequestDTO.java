package com.flav.cinema_service_invoice.invoice.domain.dtos.request;

import com.flav.cinema_service_invoice.invoice.domain.entity.InvoiceItem;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceRequestDTO {

    @Size(min = 1, message = "must have at least 1 item")
    private List<InvoiceItem> invoiceItem = new ArrayList<>();

}
