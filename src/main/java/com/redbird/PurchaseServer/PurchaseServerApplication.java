package com.redbird.PurchaseServer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@OpenAPIDefinition
@EnableEurekaClient
@EnableFeignClients
public class PurchaseServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PurchaseServerApplication.class, args);
	}

}
