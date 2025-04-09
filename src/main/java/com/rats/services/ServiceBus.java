package com.rats.services;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusErrorContext;
import com.azure.messaging.servicebus.ServiceBusException;
import com.azure.messaging.servicebus.ServiceBusFailureReason;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rats.configs.Configs;
import com.rats.configs.HandleLog;
import com.rats.interfaces.IHandleChain;
import com.rats.models.Message;
import com.rats.services.handlers.HandleAttackEnemy;
import com.rats.services.handlers.HandleAttackResult;
import com.rats.services.handlers.HandleCryptography;
import com.rats.services.handlers.HandleRegisterCampo;
import com.rats.validations.JsonValidate;
public class ServiceBus {

	static ServiceBus serviceBus;
	static ServiceBusClientBuilder serviceBuilder;
	static ServiceBusProcessorClient processorClient;
	static String connectionString = Configs.CONNECTION_STRING;
	static String topicName = Configs.TOPIC_NAME;
	static String subscriptionName = Configs.SUBSCRIPTION_NAME;


	private ServiceBus() {
	
	}

	public static ServiceBus getInstance() {
		if(serviceBus == null) {
			serviceBus = new ServiceBus();
			serviceBuilder = new ServiceBusClientBuilder();
		}	
		return serviceBus;
	}
	
	public void sendMessage(ServiceBusMessage message)
	{
		try {
			if(serviceBuilder == null) {
				serviceBuilder = new ServiceBusClientBuilder();
			}

			ServiceBusSenderClient senderClient = serviceBuilder
	            .connectionString(connectionString)
	            .sender()
	            .topicName(topicName)
	            .buildClient();
		
	    senderClient.sendMessage(message);

		} catch (Exception e) {
			System.out.println("Error sending message: " + e.getMessage());
		} finally {
			if(serviceBuilder != null) {
				serviceBuilder = null;
			}
		}
	
	}
		
	public void receiveMessages() throws InterruptedException
	{
		if(processorClient == null) {
			ServiceBusProcessorClient processorClient = serviceBuilder
				.connectionString(connectionString)
				.processor()
				.topicName(topicName)
				.subscriptionName(subscriptionName)
				.maxConcurrentCalls(1)
				.processMessage(ServiceBus::processMessage)
				.processError(context -> processError(context))
				.buildProcessorClient();
			processorClient.start();
		}
	}
	
	private static void processMessage(ServiceBusReceivedMessageContext context) {
	    ServiceBusReceivedMessage message = context.getMessage();
		    try {
				JsonValidate.isValidJson(message.getBody().toString());
				
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);

				Message messageReceived = objectMapper.readValue(message.getBody().toString(), Message.class);
				
				HandleLog.title("Message received: " + message.getBody());  

				IHandleChain handler = new HandleCryptography();
				handler.next(new HandleRegisterCampo())
                        .next(new HandleAttackResult())
						.next(new HandleAttackEnemy());
				handler.validate(messageReceived);

		    } catch (Exception e) {
				System.out.println("Error converting message to MessageReceived: " + e.getMessage());
		    }	
			
	}
	
	private static void processError(ServiceBusErrorContext context) {
		if (context.getException() instanceof ServiceBusException exception) {
			if (exception.getReason() == ServiceBusFailureReason.GENERAL_ERROR) {
				System.out.println("General error occurred: " + exception.getMessage());
			} else if (exception.getReason() == ServiceBusFailureReason.MESSAGE_LOCK_LOST) {
				System.out.println("Message lock lost: " + exception.getMessage());
			} else if (exception.getReason() == ServiceBusFailureReason.SERVICE_BUSY) {
				System.out.println("Service is busy: " + exception.getMessage());
			}
		} else {
			System.out.println("Error occurred: " + context.getException().getMessage());
		}
	}


	public void close() {
		if(processorClient != null) {
			processorClient.close();
		}
		if(serviceBuilder != null) {
			serviceBuilder = null;
		}
	}


}
