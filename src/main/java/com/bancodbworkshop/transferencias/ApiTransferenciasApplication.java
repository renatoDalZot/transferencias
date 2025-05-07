package com.bancodbworkshop.transferencias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//		(exclude = {org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration.class})
public class ApiTransferenciasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiTransferenciasApplication.class, args);
	}

}
