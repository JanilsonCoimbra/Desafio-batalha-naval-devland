package com.rats.services.handlers;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.rats.configs.Configs;
import com.rats.interfaces.EventsEnum;
import com.rats.interfaces.ICommunication;
import com.rats.interfaces.IHandleChain;
import com.rats.models.DirectorMessage;
import com.rats.models.Message;
import com.rats.services.ServiceBus;

public class HandleAttackEnemy implements IHandleChain {
    private IHandleChain nextHandler;
    
        @Override
        public IHandleChain next(IHandleChain nextHandler) {
            this.nextHandler = nextHandler;
            return this.nextHandler;
        }
    
        @Override
        public ICommunication validate(ICommunication request) {

            if (request.getEvento() == EventsEnum.LiberacaoAtaque && request.getNavioDestino().equals(Configs.SUBSCRIPTION_NAME)) {
                
                System.out.println("Atack: Processing message.");
                System.out.println("------------------------------------------------------------");  
                
                Message message = DirectorMessage.createAttackMessage(request.getCorrelationId(), (byte) 1, (byte) 2);
                ServiceBusMessage messageService = new ServiceBusMessage(message.toString());
                messageService.setCorrelationId(message.getCorrelationId());

                ServiceBus service = ServiceBus.getInstance();
                service.sendMessage(messageService);
            }

            if (nextHandler != null) {
                return nextHandler.validate(request);

            }
            
            return request;
        } 
}
