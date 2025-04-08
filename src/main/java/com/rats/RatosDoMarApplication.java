package com.rats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.rats.configs.HandleLog;
import com.rats.services.ServiceBus;

@SpringBootApplication
public class RatosDoMarApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(RatosDoMarApplication.class, args);
		HandleLog.title("Iniciando Ratos do Mar");

		ServiceBus service = ServiceBus.getInstance();
		service.receiveMessages();

	}

}
