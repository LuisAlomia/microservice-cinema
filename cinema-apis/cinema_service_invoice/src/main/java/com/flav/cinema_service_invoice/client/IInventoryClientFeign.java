package com.flav.cinema_service_invoice.client;

import com.flav.cinema_service_invoice.client.dtos.TicketResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "cinema-service-inventory", url = "http://localhost:9001/inventory")
public interface IInventoryClientFeign {

    @GetMapping("/{idMovie}")
    public TicketResponseDTO inStock(@PathVariable("idMovie") int idMovie);

    @GetMapping("/take-out-stock/{idMovie}")
    public TicketResponseDTO takeOutStock(@PathVariable("idMovie") int idMovie);

}
