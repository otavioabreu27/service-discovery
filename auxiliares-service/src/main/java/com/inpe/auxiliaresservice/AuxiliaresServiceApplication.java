package com.inpe.auxiliaresservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuxiliaresServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuxiliaresServiceApplication.class, args);
	}

}
