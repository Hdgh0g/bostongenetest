package com.hdgh0g.bostongenetest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class BostongenetestApplication {

	public static void main(String[] args) {
		SpringApplication.run(BostongenetestApplication.class, args);
	}
}
