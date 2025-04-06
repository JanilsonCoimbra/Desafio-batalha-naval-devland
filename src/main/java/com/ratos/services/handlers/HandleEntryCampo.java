package com.ratos.services.handlers;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.ratos.interfaces.EventsEnum;
import com.ratos.interfaces.IComunication;
import com.ratos.interfaces.IHandleChain;
import com.ratos.models.DiretorMessage;
import com.ratos.models.Message;
import com.ratos.services.Servicebus;

public class HandleEntryCampo implements IHandleChain {
    
        private IHandleChain nextHandler;
    
        @Override
        public IHandleChain next(IHandleChain nextHandler) {
            this.nextHandler = nextHandler;
            return this.nextHandler;
        }
    
        @Override
        public IComunication validate(IComunication request) {


                if (request.getEvento() == EventsEnum.CampoLiberadoParaRegistro) {
                    System.out.println("------------------------------------------------------------");
                    System.out.println("Campo de batalha encontrado:  "+ request.getCorrelationId());
                    System.out.println("------------------------------------------------------------");

                    Message messageBuilder  = DiretorMessage.createMessageSubscription( request.getCorrelationId());
                    
                    ServiceBusMessage messageService = new ServiceBusMessage(messageBuilder.toString());

                    Servicebus service = new Servicebus();
                    service.sendMessage(messageService);

                    return request;
                }

                if (nextHandler != null) {
                    return nextHandler.validate(request);
                }
            
            return request;
        }
}
