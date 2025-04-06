package com.ratos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ratos.services.ServiceBus;

@SpringBootApplication
public class RatosdomarApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(RatosdomarApplication.class, args);

		
		ServiceBus service = new ServiceBus();
		service.receiveMessages();

	}

}
