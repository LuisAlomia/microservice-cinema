package com.flav.cinema_service_invoice.invoice.persistence.repository;

import com.flav.cinema_service_invoice.invoice.domain.entity.Invoice;
import com.flav.cinema_service_invoice.invoice.domain.repository.IInvoiceRepository;
import com.flav.cinema_service_invoice.invoice.persistence.repository.IInvoiceRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InvoiceRepositoryImpl implements IInvoiceRepository {

    private final IInvoiceRepositoryJpa repositoryJpa;

    public InvoiceRepositoryImpl(IInvoiceRepositoryJpa repositoryJpa) {
        this.repositoryJpa = repositoryJpa;
    }

    @Override
    public List<Invoice> findAll() {
        return repositoryJpa.findAll();
    }

    @Override
    public Optional<Invoice> findOne(Long idVoice) {
        return repositoryJpa.findById(idVoice);
    }

    @Override
    public Invoice create(Invoice invoice) {
        return repositoryJpa.save(invoice);
    }

}
