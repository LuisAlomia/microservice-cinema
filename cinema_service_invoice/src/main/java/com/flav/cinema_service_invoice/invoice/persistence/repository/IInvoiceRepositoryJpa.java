package com.flav.cinema_service_invoice.invoice.persistence.repository;

import com.flav.cinema_service_invoice.invoice.domain.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IInvoiceRepositoryJpa extends JpaRepository<Invoice, Long> {
}
