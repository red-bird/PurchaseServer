package com.redbird.PurchaseServer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class PurchaseServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PurchaseServerApplication.class, args);
	}

}
