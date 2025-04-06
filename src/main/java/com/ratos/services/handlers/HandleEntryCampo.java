package com.ratos.services.handlers;

import java.lang.module.ModuleDescriptor.Builder;
import java.security.Provider.Service;
import java.util.Objects;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.ratos.interfaces.EventsEnum;
import com.ratos.interfaces.IComunication;
import com.ratos.interfaces.IHandleChain;
import com.ratos.models.BuilderMessage;
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


                    // IComunication message = new Message();
                    // message.setCorrelationId(request.getCorrelationId());
                    // message.setEvento(EventsEnum.RegistroNavio);
                    // message.setOrigem("ratos_do_mar");
                    // message.setConteudo("{\"nomeNavio\":\"ratos_do_mar\",\"posicaoCentral\":{\"x\":20,\"y\":20},\"orientacao\":\"vertical\"}");

                    BuilderMessage builderMessage = new BuilderMessage();
                    Message messageBuilder  = builderMessage 
                            .setCorrelationId(request.getCorrelationId())
                            .setEvento(EventsEnum.RegistroNavio)
                            .setOrigem("ratos_do_mar")
                            .setConteudo("{\"nomeNavio\":\"ratos_do_mar\",\"posicaoCentral\":{\"x\":20,\"y\":20},\"orientacao\":\"vertical\"}")
                            .build();

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
