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
import com.rats.configs.AppConfig;
import com.rats.configs.ApplicationContextProvider;
import com.rats.configs.HandleLog;
import com.rats.interfaces.IHandleChain;
import com.rats.models.Message;
import com.rats.services.handlers.HandleAttackEnemy;
import com.rats.services.handlers.HandleAttackResult;
import com.rats.services.handlers.HandleCryptography;
import com.rats.services.handlers.HandleEndGame;
import com.rats.services.handlers.HandleRegisterCampo;
import com.rats.services.handlers.HandleScreen;
import com.rats.validations.JsonValidate;
public class ServiceBus {
	static AppConfig appConfig = ApplicationContextProvider.getApplicationContext().getBean(AppConfig.class);
	static ServiceBus serviceBus;


	// static AppConfig appConfig = new AppConfig();
	static ServiceBusClientBuilder serviceBuilder;
	static ServiceBusProcessorClient processorClient;
	static String connectionString;
	static String topicName;
	static String subscriptionName;

	static {
		connectionString = appConfig.getConnectionString();
		topicName = appConfig.getTopicName();
		subscriptionName = appConfig.getSubscriptionName();
	}

	private ServiceBus() {
	
	}

    public static ServiceBus getInstance() {
		if (serviceBus == null) {
			try {
				serviceBus = new ServiceBus();
				serviceBuilder = new ServiceBusClientBuilder();
				System.out.println("ServiceBusClientBuilder inicializado com sucesso.");
			} catch (Exception e) {
				System.err.println("Erro ao inicializar ServiceBusClientBuilder: " + e.getMessage());
				e.printStackTrace();
			}
		}
		return serviceBus;
	}

    public void sendMessage(ServiceBusMessage message) {
        try {
            if (serviceBuilder == null) {
                serviceBuilder = new ServiceBusClientBuilder();
            }

            System.out.println("Enviando mensagem para o tópico: " + topicName);
            ServiceBusSenderClient senderClient = serviceBuilder
                .connectionString(connectionString)
                .sender()
                .topicName(topicName)
                .buildClient();

            senderClient.sendMessage(message);
            System.out.println("Mensagem enviada com sucesso.");

        } catch (Exception e) {
            System.err.println("Erro ao enviar mensagem: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (serviceBuilder != null) {
                serviceBuilder = null;
            }
        }
    }

    public void receiveMessages() throws InterruptedException {
		System.out.println("Iniciando ServiceBus com as seguintes configurações:");
        System.out.println("Connection String: " + connectionString);
        System.out.println("Topic Name: " + topicName);
        System.out.println("Subscription Name: " + subscriptionName);
        try {
            if (processorClient == null) {
                System.out.println("Iniciando o processamento de mensagens...");
                processorClient = serviceBuilder
                    .connectionString(connectionString)
                    .processor()
                    .topicName(topicName)
                    .subscriptionName(subscriptionName)
                    .maxConcurrentCalls(1)
                    .processMessage(ServiceBus::processMessage)
                    .processError(context -> processError(context))
                    .buildProcessorClient();
                processorClient.start();
                System.out.println("Processador de mensagens iniciado com sucesso.");
            } else {
				System.out.println("Processador de mensagens já está em execução.");
			}
        } catch (Exception e) {
            System.err.println("Erro ao iniciar o processamento de mensagens: " + e.getMessage());
            e.printStackTrace();
        }
    }
	
	private static void processMessage(ServiceBusReceivedMessageContext context) {
	    ServiceBusReceivedMessage message = context.getMessage();
		    try {
				JsonValidate.isValidJson(message.getBody().toString());
				
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);

				Message messageReceived = objectMapper.readValue(message.getBody().toString(), Message.class);

				IHandleChain handler = new HandleCryptography();
				handler
				.next(new HandleRegisterCampo())
				.next(new HandleAttackResult())
				.next(new HandleAttackEnemy())
				.next(new HandleScreen())
				.next(new HandleEndGame())
				;
				handler.validate(messageReceived);

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


	public static void close() {
		if (processorClient != null) {
			processorClient.stop();
			HandleLog.title("Stopping message processor...");
		}
		if (serviceBuilder != null) {
			HandleLog.title("ServiceBus client builder cleared.");
		}
	}


}
