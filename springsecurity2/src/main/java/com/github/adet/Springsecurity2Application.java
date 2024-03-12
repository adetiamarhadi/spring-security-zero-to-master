package com.github.adet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class Springsecurity2Application {

	public static void main(String[] args) {
		SpringApplication.run(Springsecurity2Application.class, args);
	}

}
