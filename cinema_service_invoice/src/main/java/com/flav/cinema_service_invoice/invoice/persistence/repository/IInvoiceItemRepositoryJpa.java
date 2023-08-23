package com.flav.cinema_service_invoice.invoice.persistence.repository;

import com.flav.cinema_service_invoice.invoice.domain.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInvoiceItemRepositoryJpa extends JpaRepository<InvoiceItem, Long> {
}
