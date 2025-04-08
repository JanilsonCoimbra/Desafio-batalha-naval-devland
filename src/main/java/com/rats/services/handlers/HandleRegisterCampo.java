package com.rats.services.handlers;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.rats.interfaces.EventsEnum;
import com.rats.interfaces.ICommunication;
import com.rats.interfaces.IHandleChain;
import com.rats.models.DirectorMessage;
import com.rats.models.Message;
import com.rats.services.ServiceBus;

public class HandleRegisterCampo implements IHandleChain {
    
        private IHandleChain nextHandler;
    
        @Override
        public IHandleChain next(IHandleChain nextHandler) {
            this.nextHandler = nextHandler;
            return this.nextHandler;
        }
    
        @Override
        public ICommunication validate(ICommunication request) {


                if (request.getEvento() == EventsEnum.CampoLiberadoParaRegistro) {
                    System.out.println("------------------------------------------------------------");
                    System.out.println("Campo de batalha encontrado:  "+ request.getCorrelationId());
                    System.out.println("------------------------------------------------------------");

                    Message message  = DirectorMessage.createRegisterMessage( request.getCorrelationId());
                    
                    ServiceBusMessage messageService = new ServiceBusMessage(message.toString());

                    ServiceBus service = ServiceBus.getInstance();
                    service.sendMessage(messageService);

                    return request;
                }

                if(request.getEvento() == EventsEnum.RegistrarNovamente) {
                    System.out.println("------------------------------------------------------------");
                    System.out.println("Novo registro no Campo:  "+ request.getCorrelationId());
                    System.out.println("------------------------------------------------------------");

                    Message message  = DirectorMessage.createRegisterMessage( request.getCorrelationId());
                    
                    ServiceBusMessage messageService = new ServiceBusMessage(message.toString());

                    ServiceBus service = ServiceBus.getInstance();
                    service.sendMessage(messageService);

                }

                if (nextHandler != null) {
                    return nextHandler.validate(request);
                }
            
            return request;
        }
}
