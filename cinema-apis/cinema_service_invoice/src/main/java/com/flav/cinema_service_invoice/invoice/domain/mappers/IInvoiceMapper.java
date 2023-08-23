package com.flav.cinema_service_invoice.invoice.domain.mappers;

import com.flav.cinema_service_invoice.invoice.domain.dtos.reponse.InvoiceResponseDTO;
import com.flav.cinema_service_invoice.invoice.domain.dtos.request.InvoiceRequestDTO;
import com.flav.cinema_service_invoice.invoice.domain.entity.Invoice;

public interface IInvoiceMapper {

    public Invoice toEntity(InvoiceRequestDTO invoice);
    public InvoiceResponseDTO toResponseDTO(Invoice invoice);

}
