package com.flav.cinema_service_invoice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CinemaServiceInvoiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemaServiceInvoiceApplication.class, args);
	}

}
