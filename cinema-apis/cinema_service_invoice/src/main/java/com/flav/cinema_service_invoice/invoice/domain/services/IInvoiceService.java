package com.flav.cinema_service_invoice.invoice.domain.services;

import com.flav.cinema_service_invoice.invoice.domain.dtos.reponse.InvoiceResponseDTO;
import com.flav.cinema_service_invoice.invoice.domain.dtos.request.InvoiceRequestDTO;

import java.util.List;

public interface IInvoiceService {

    public List<InvoiceResponseDTO> findAll();
    public InvoiceResponseDTO findOne(Long idVoice);
    public InvoiceResponseDTO create(InvoiceRequestDTO invoice);

}
