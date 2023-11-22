package com.inpe.focosservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
public class FocosServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FocosServiceApplication.class, args);
	}

}
