package com.rats.services;

import java.util.concurrent.TimeUnit;

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
			String setLog = Configs.SUBSCRIPTION_NAME;
		    try {
				JsonValidate.isValidJson(message.getBody().toString());
				
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);

				Message messageReceived;
		        messageReceived = objectMapper.readValue(message.getBody().toString(), Message.class);
				
				System.out.printf("Contents from %s: %s%n", setLog, message.getBody());
				System.out.println("------------------------------------------------------------");

				if(messageReceived.getNavioDestino().equals(setLog)) {
					System.out.printf("Contents from %s: %s%n", setLog, message.getBody());
					System.out.println("------------------------------------------------------------");
				}
				


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
	    System.out.printf("Error when receiving messages from namespace: '%s'. Entity: '%s'%n",
	        context.getFullyQualifiedNamespace(), context.getEntityPath());

	    if (!(context.getException() instanceof ServiceBusException)) {
	        System.out.printf("Non-ServiceBusException occurred: %s%n", context.getException());
	        return;
	    }

	    ServiceBusException exception = (ServiceBusException) context.getException();
	    ServiceBusFailureReason reason = exception.getReason();

	    if (reason == ServiceBusFailureReason.MESSAGING_ENTITY_DISABLED
	        || reason == ServiceBusFailureReason.MESSAGING_ENTITY_NOT_FOUND
	        || reason == ServiceBusFailureReason.UNAUTHORIZED) {
	        System.out.printf("An unrecoverable error occurred. Stopping processing with reason %s: %s%n",
	            reason, exception.getMessage());
	        
	        if (reason == ServiceBusFailureReason.MESSAGING_ENTITY_NOT_FOUND) {
	            System.out.println("The specified topic or subscription does not exist. Please verify the names.");
	            System.out.printf("Topic: %s, Subscription: %s%n", topicName, subscriptionName);
	        }
	    } else if (reason == ServiceBusFailureReason.MESSAGE_LOCK_LOST) {
	        System.out.printf("Message lock lost for message: %s%n", context.getException());
	    } else if (reason == ServiceBusFailureReason.SERVICE_BUSY) {
	        try {
	            // Choosing an arbitrary amount of time to wait until trying again.
	            TimeUnit.SECONDS.sleep(1);
	        } catch (InterruptedException e) {
	            System.err.println("Unable to sleep for period of time");
	        }
	    } else {
	        System.out.printf("Error source %s, reason %s, message: %s%n", context.getErrorSource(),
	            reason, context.getException());
	    }
	}


}
