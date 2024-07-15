package com.example.HumidityMicroService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HumidityMicroServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HumidityMicroServiceApplication.class, args);
	}

}
