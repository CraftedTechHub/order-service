package com.KalaroApplication.KALARO_ORDERS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class KalaroOrdersApplication {

	public static void main(String[] args) {
		SpringApplication.run(KalaroOrdersApplication.class, args);
	}
}
