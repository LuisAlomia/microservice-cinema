package com.flav.cinema_service_movie.client;

import com.flav.cinema_service_movie.client.dtos.TicketRequestDTO;
import com.flav.cinema_service_movie.client.dtos.TicketResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "cinema-service-inventory", url = "http://localhost:9001/inventory")
public interface IInventoryClientFeign {

    @PostMapping
    public ResponseEntity<TicketResponseDTO> addTicket(@RequestBody TicketRequestDTO ticket);

    @GetMapping("/{idMovie}")
    public ResponseEntity<TicketResponseDTO> inStock(@PathVariable("idMovie") Long idMovie);

}
