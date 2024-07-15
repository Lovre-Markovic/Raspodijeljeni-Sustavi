package com.example.TemperatureMicroService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TemperatureMicroServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TemperatureMicroServiceApplication.class, args);
	}

}
