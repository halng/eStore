package com.e.store.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class EmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailApplication.class, args);
	}

}
