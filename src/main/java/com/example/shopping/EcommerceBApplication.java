package com.example.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.shopping")
public class EcommerceBApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceBApplication.class, args);
	}

}
