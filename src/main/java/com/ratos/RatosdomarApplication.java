package com.ratos;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ratos.models.ShipState;
import com.ratos.services.Servicebus;

@SpringBootApplication
public class RatosdomarApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(RatosdomarApplication.class, args);
		Servicebus service = new Servicebus();
		// service.sendMessage();
		service.receiveMessages();
		// service.sendMessage(service.createMessages());


		ShipState shipState = ShipState.getInstance();

		while (true) {
	        TimeUnit.SECONDS.sleep(1);
	    }

	}

}
