package com.ratos.services;

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

public class Servicebus {
	
	static String connectionString = "Endpoint=sb://servdevland.servicebus.windows.net/;SharedAccessKeyName=casaratolandia;SharedAccessKey=MUt2vhyqM/TwWxhad+DzI2L1wjyifG3wP+ASbPh+dYc=";
	static String topicName = "desafio.batalha_naval.casaratolandia";
	static String subscriptionName = "rato_do_mar";
	
	public static void sendMessage(ServiceBusMessage message)
	{
	    ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
	            .connectionString(connectionString)
	            .sender()
	            .topicName(topicName)
	            .buildClient();

	    senderClient.sendMessage(message);
	    System.out.println("Sent a single message to the topic: " + topicName);
	}
	
	public ServiceBusMessage createMessages()
	{
	    ServiceBusMessage messages = new ServiceBusMessage("First message");
	    return messages;
	}
	
	public void receiveMessages() throws InterruptedException
	{
	    ServiceBusProcessorClient processorClient = new ServiceBusClientBuilder()
	        .connectionString(connectionString)
	        .processor()
	        .topicName(topicName)
	        .subscriptionName(subscriptionName)
			.processMessage(Servicebus::processMessage)
	        .processError(context -> processError(context))
	        .buildProcessorClient();

	    System.out.println("Starting the processor");
	    processorClient.start();
	}
	
	
	private static void processMessage(ServiceBusReceivedMessageContext context) {
	    ServiceBusReceivedMessage message = context.getMessage();
	    System.out.printf("Processing message. Session: %s, Sequence #: %s. Contents: %s%n", message.getMessageId(),
	        message.getSequenceNumber(), message.getBody());
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
