package com.hdgh0g.bostongenetest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCircuitBreaker
@EnableScheduling
public class BostongenetestApplication {

	public static void main(String[] args) {
		SpringApplication.run(BostongenetestApplication.class, args);
	}
}
