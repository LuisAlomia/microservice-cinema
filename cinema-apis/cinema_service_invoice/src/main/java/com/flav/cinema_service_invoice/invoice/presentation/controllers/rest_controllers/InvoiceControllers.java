package com.flav.cinema_service_invoice.invoice.presentation.controllers.rest_controllers;

import com.flav.cinema_service_invoice.invoice.domain.dtos.reponse.InvoiceResponseDTO;
import com.flav.cinema_service_invoice.invoice.domain.dtos.request.InvoiceRequestDTO;
import com.flav.cinema_service_invoice.invoice.domain.services.IInvoiceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cinema/api/v1/invoices")
public class InvoiceControllers {

    private final IInvoiceService service;

    public InvoiceControllers(IInvoiceService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<InvoiceResponseDTO>> findAll() {
        return ResponseEntity.status(200).body(service.findAll());
    }

    @GetMapping("/{idInvoice}")
    public ResponseEntity<InvoiceResponseDTO> findOne(@PathVariable("idInvoice") Long idInvoice) {
        return ResponseEntity.status(200).body(service.findOne(idInvoice));
    }

    @PostMapping
    public ResponseEntity<InvoiceResponseDTO> create(@Valid @RequestBody InvoiceRequestDTO invoice) {
        return ResponseEntity.status(201).body(service.create(invoice));
    }

}
