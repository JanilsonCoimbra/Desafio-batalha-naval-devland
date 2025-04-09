package com.rats.services.handlers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rats.configs.Configs;
import com.rats.configs.HandleLog;
import com.rats.interfaces.EventsEnum;
import com.rats.interfaces.ICommunication;
import com.rats.interfaces.IHandleChain;
import com.rats.models.AttackResultContent;
import com.rats.models.ShipModel;
import com.rats.validations.CalculadoraDeBatalha;
public class HandleAttackResult implements IHandleChain {

    private IHandleChain nextHandler;
    ShipModel shipModel = ShipModel.getShipModel();


        @Override
        public IHandleChain next(IHandleChain nextHandler) {
            this.nextHandler = nextHandler;
            return this.nextHandler;
        }
    
        @Override
        public ICommunication validate(ICommunication request) {

            if (request.getEvento() == EventsEnum.ResultadoAtaqueEfetuado && request.getNavioDestino().equals(Configs.SUBSCRIPTION_NAME)) {
                HandleLog.title("Ataque recebido: ");  
                
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);
                
                try {
                    
                    AttackResultContent messageReceived = objectMapper.readValue(request.getConteudo().toString(), AttackResultContent.class);
                    if (messageReceived.getDistanciaAproximada() <= 7) {
                        shipModel.setShootLevel(1);

                        System.out.println("Distancia menor que 7: " + messageReceived.getDistanciaAproximada());

                        shipModel.distanceApproximate = String.valueOf(messageReceived.getDistanciaAproximada());
                        List<Long[]> wrappedPositions = new ArrayList<>();
                        CalculadoraDeBatalha.calcularPosicoesPossiveis(messageReceived.getPosicao().getX(), messageReceived.getPosicao().getY(), messageReceived.getDistanciaAproximada())
                            .forEach(pos -> wrappedPositions.add(Arrays.stream(pos).boxed().toArray(Long[]::new)));
                            shipModel.secondSetShoot.add(wrappedPositions);
                        
                    }
                } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
                    System.err.println("Error processing JSON: " + e.getMessage());
                }
            }

            if (nextHandler != null) {
                return nextHandler.validate(request);

            }
            
            return request;
        }

}
