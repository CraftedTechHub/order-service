package com.KalaroApplication.KALARO_ORDERS;

import org.springframework.boot.SpringApplication;

public class TestOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(KalaroOrdersApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
