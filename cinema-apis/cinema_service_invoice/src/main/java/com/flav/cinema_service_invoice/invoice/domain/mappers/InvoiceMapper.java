package com.flav.cinema_service_invoice.invoice.domain.mappers;

import com.flav.cinema_service_invoice.invoice.domain.dtos.reponse.InvoiceItemResponseDTO;
import com.flav.cinema_service_invoice.invoice.domain.dtos.reponse.InvoiceMovieResponseDTO;
import com.flav.cinema_service_invoice.invoice.domain.dtos.reponse.InvoiceResponseDTO;
import com.flav.cinema_service_invoice.invoice.domain.dtos.request.InvoiceRequestDTO;
import com.flav.cinema_service_invoice.invoice.domain.entity.Invoice;
import org.springframework.stereotype.Component;

@Component
public class InvoiceMapper implements IInvoiceMapper {

    @Override
    public Invoice toEntity(InvoiceRequestDTO invoice) {
        return Invoice.builder()
                .invoiceItem(invoice.getInvoiceItem())
                .build();
    }

    @Override
    public InvoiceResponseDTO toResponseDTO(Invoice invoice) {
        return InvoiceResponseDTO.builder()
                .id(invoice.getId())
                .createAt(invoice.getCreateAt())
                .code(invoice.getCode())
                .amount(invoice.getAmount())
                .total(invoice.getTotal())
                .invoiceItem(invoice.getInvoiceItem()
                        .stream()
                        .map(invoiceItem -> InvoiceItemResponseDTO
                                .builder()
                                .id(invoiceItem.getId())
                                .idMovie(invoiceItem.getIdMovie())
                                .movie(InvoiceMovieResponseDTO
                                        .builder()
                                        .id(invoiceItem.getMovie().getId())
                                        .title(invoiceItem.getMovie().getTitle())
                                        .description(invoiceItem.getMovie().getDescription())
                                        .image(invoiceItem.getMovie().getImage())
                                        .video(invoiceItem.getMovie().getVideo())
                                        .releaseDate(invoiceItem.getMovie().getReleaseDate())
                                        .price(invoiceItem.getMovie().getPrice())
                                        .category(invoiceItem.getMovie().getCategory())
                                        .build())
                                .build())
                        .toList()
                )
                .build();
    }
}
