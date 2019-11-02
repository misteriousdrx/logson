package com.example.liblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibLogApplication.class, args);

		Logson.setDomain("domínio teste");
		Logson.setServiceId("serviço_01");

	}

}
