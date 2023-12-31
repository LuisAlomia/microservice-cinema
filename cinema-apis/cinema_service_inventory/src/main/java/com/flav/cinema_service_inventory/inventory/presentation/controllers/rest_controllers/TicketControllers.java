package com.flav.cinema_service_inventory.inventory.presentation.controllers.rest_controllers;

import com.flav.cinema_service_inventory.inventory.domain.dtos.reponse.TicketResponseDTO;
import com.flav.cinema_service_inventory.inventory.domain.dtos.request.TicketRequestDTO;
import com.flav.cinema_service_inventory.inventory.domain.services.ITicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/cinema/api/v1/inventory")
public class TicketControllers {

    private final ITicketService service;

    public TicketControllers(ITicketService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> showTicket() {
        return ResponseEntity.status(HttpStatus.OK).body(service.showStock());
    }

    @GetMapping("/{idMovie}")
    public ResponseEntity<TicketResponseDTO> inStock(@PathVariable("idMovie") int idMovie) {
        return ResponseEntity.status(HttpStatus.OK).body(service.inStock(idMovie));
    }

    @PostMapping
    public ResponseEntity<TicketResponseDTO> addTicket(@Valid @RequestBody TicketRequestDTO ticket) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addStock(ticket));
    }

    @GetMapping("/take-out-stock/{idMovie}")
    public ResponseEntity<TicketResponseDTO> takeOutStock(@PathVariable("idMovie") int idMovie) {
        return ResponseEntity.status(HttpStatus.OK).body(service.takeOutStock(idMovie));
    }

}
