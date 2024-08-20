package com.example.bloomgift;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BloomgiftApplication {

	public static void main(String[] args) {
		SpringApplication.run(BloomgiftApplication.class, args);
	}

}
