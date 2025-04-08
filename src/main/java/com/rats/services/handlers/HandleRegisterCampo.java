package com.rats.services.handlers;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.rats.configs.HandleLog;
import com.rats.interfaces.EventsEnum;
import com.rats.interfaces.ICommunication;
import com.rats.interfaces.IHandleChain;
import com.rats.models.DirectorMessage;
import com.rats.models.Message;
import com.rats.services.ServiceBus;

public class HandleRegisterCampo implements IHandleChain {
        // TODO: put SHOOT_LEVEL = 0
        private IHandleChain nextHandler;
    
        @Override
        public IHandleChain next(IHandleChain nextHandler) {
            this.nextHandler = nextHandler;
            return this.nextHandler;
        }
    
        @Override
        public ICommunication validate(ICommunication request) {


                if (request.getEvento() == EventsEnum.CampoLiberadoParaRegistro) {
                    HandleLog.title("Campo de batalha encontrado "+request.getCorrelationId());  

                    Message message  = DirectorMessage.createRegisterMessage( request.getCorrelationId());
                    
                    ServiceBusMessage messageService = new ServiceBusMessage(message.toString());

                    ServiceBus service = ServiceBus.getInstance();
                    service.sendMessage(messageService);

                    return request;
                }

                if(request.getEvento() == EventsEnum.RegistrarNovamente) {
                    HandleLog.title("Novo Registro "+request.getCorrelationId());  


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
