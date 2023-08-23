package com.flav.cinema_service_invoice.invoice.domain.repository;

import com.flav.cinema_service_invoice.invoice.domain.entity.Invoice;

import java.util.List;
import java.util.Optional;

public interface IInvoiceRepository {

    public List<Invoice> findAll();
    public Optional<Invoice> findOne(Long idVoice);
    public Invoice create(Invoice invoice);

}
