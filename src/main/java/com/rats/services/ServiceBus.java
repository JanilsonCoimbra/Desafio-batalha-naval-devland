package com.rats.services;

import org.springframework.stereotype.Service;

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
import com.rats.models.Message;
import com.rats.models.ShipModel;
import com.rats.services.handlers.HandleAttackEnemy;
import com.rats.services.handlers.HandleAttackResult;
import com.rats.services.handlers.HandleCryptography;
import com.rats.services.handlers.HandleRegisterCampo;
import com.rats.validations.JsonValidate;

@Service
public class ServiceBus {


    private final ServiceBusClientBuilder serviceBuilder;
    private ServiceBusProcessorClient processorClient;
    private final String connectionString = Configs.getInstance().CONNECTION_STRING;
    private final String topicName = Configs.getInstance().TOPIC_NAME;
    private final String subscriptionName = Configs.getInstance().SUBSCRIPTION_NAME;

    public ServiceBus() {
        this.serviceBuilder = new ServiceBusClientBuilder();
    }

    public void sendMessage(ServiceBusMessage message) {
        try (ServiceBusSenderClient senderClient = serviceBuilder
                .connectionString(connectionString)
                .sender()
                .topicName(topicName)
                .buildClient()) {
            senderClient.sendMessage(message);
        } catch (Exception e) {
            System.out.println("Error sending message: " + e.getMessage());
        }
    }

    public void receiveMessages() throws InterruptedException {
        if (processorClient == null) {
            processorClient = serviceBuilder
                .connectionString(connectionString)
                .processor()
                .topicName(topicName)
                .subscriptionName(subscriptionName)
                .maxConcurrentCalls(1)
                .processMessage(this::processMessage)
                .processError(ServiceBus::processError)
                .buildProcessorClient();
            processorClient.start();
        }
    }

    private void processMessage(ServiceBusReceivedMessageContext context) {
        ServiceBusReceivedMessage message = context.getMessage();
        try {
            JsonValidate.isValidJson(message.getBody().toString());

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);

            Message messageReceived = objectMapper.readValue(message.getBody().toString(), Message.class);

            HandleLog.title("Message received: " + message.getBody());
            ShipModel shipModel = ShipModel.getShipModel();
            // Use os handlers injetados
            HandleCryptography handleCryptography = new HandleCryptography();
            handleCryptography.next(new HandleRegisterCampo(this))
                .next(new HandleAttackResult(shipModel))
                .next(new HandleAttackEnemy(this, shipModel));

            handleCryptography.validate(messageReceived);

        } catch (Exception e) {
            HandleLog.title("Error deserializar message: " + message.getBody());
            HandleLog.title("Error converting message to MessageReceived: " + e.getMessage());
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
}
