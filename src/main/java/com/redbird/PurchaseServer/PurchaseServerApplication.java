package com.redbird.PurchaseServer;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.TimeZone;

@SpringBootApplication
@OpenAPIDefinition
@EnableEurekaClient
@EnableFeignClients
public class PurchaseServerApplication {

	@Value("${spring.jpa.hibernate.jdbc.time_zone}")
	private String timezone;

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone(timezone));
	}

	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
				.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build())
				.circuitBreakerConfig(CircuitBreakerConfig.custom().slidingWindowSize(20).failureRateThreshold(33.3F)
						.slowCallRateThreshold(33.3F).build())
				.build());
	}

	public static void main(String[] args) {
		SpringApplication.run(PurchaseServerApplication.class, args);
	}

}
