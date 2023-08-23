package com.flav.cinema_service_invoice.utils;

import com.flav.cinema_service_invoice.invoice.domain.entity.Invoice;
import com.flav.cinema_service_invoice.invoice.domain.entity.InvoiceItem;
import com.flav.cinema_service_invoice.invoice.persistence.repository.IInvoiceItemRepositoryJpa;
import com.flav.cinema_service_invoice.invoice.persistence.repository.IInvoiceRepositoryJpa;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class DataLoader implements CommandLineRunner {

    private final IInvoiceRepositoryJpa invoiceRepositoryJpa;
    private final IInvoiceItemRepositoryJpa invoiceItemRepositoryJpa;

    public DataLoader(IInvoiceRepositoryJpa invoiceRepositoryJpa, IInvoiceItemRepositoryJpa invoiceItemRepositoryJpa) {
        this.invoiceRepositoryJpa = invoiceRepositoryJpa;
        this.invoiceItemRepositoryJpa = invoiceItemRepositoryJpa;
    }

    @Override
    public void run(String... args) throws Exception {
        InvoiceItem item1 = InvoiceItem.builder().idMovie(1L).build();
        InvoiceItem item2 = InvoiceItem.builder().idMovie(2L).build();

        var listItem = List.of(item1, item2);

        if (invoiceItemRepositoryJpa.findAll().isEmpty()) {
            invoiceItemRepositoryJpa.saveAll(listItem);
        }

        Invoice invoice1 = Invoice.builder().createAt(new Date()).code(UUID.randomUUID().toString()).amount(2).total(16).invoiceItem(listItem).build();

        if (invoiceRepositoryJpa.findAll().isEmpty()) {
            invoiceRepositoryJpa.saveAll(List.of(invoice1));
        }

    }
}
