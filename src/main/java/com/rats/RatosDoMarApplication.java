package com.rats;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rats.services.ServiceBus;

@SpringBootApplication
public class RatosDoMarApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(RatosDoMarApplication.class, args);

		
		ServiceBus service = new ServiceBus();
		service.receiveMessages();

	}

}
