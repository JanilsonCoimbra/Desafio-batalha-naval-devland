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
                    AttackResultContent messageReceived = objectMapper.readValue(request.getConteudo(), AttackResultContent.class);
                    System.out.println("Distancia aproximada: " + messageReceived.getDistanciaAproximada());

                    if (messageReceived.getDistanciaAproximada() <= 7 && shipModel.getShootLevel() == 0) {
                        shipModel.setShootLevel(1);

                        System.out.println("Distancia menor que 7: " + messageReceived.getDistanciaAproximada());

                        shipModel.distanceApproximate = String.valueOf(messageReceived.getDistanciaAproximada());
                        List<long[]> wrappedPositions = new ArrayList<>();
                        wrappedPositions = CalculadoraDeBatalha.calcularPosicoesPossiveis(messageReceived.getPosicao().getX(), messageReceived.getPosicao().getY(), messageReceived.getDistanciaAproximada());
                        shipModel.secondSetShoot.add(wrappedPositions);
                    } else if (messageReceived.isAcertou() && shipModel.getShootLevel() == 1) {
                        shipModel.setShootLevel(2);
                        System.out.println("Acertou: " + messageReceived.isAcertou());
                        
                        List<long[]> wrappedPositions = new ArrayList<>();
                        int x = messageReceived.getPosicao().getX().intValue();
                        int y = messageReceived.getPosicao().getY().intValue();
                        wrappedPositions.add(new long[] {x - 1, y});
                        wrappedPositions.add(new long[] {x, y - 1});
                        wrappedPositions.add(new long[] {x - 1, y});
                        wrappedPositions.add(new long[] {x, y - 1});
                        shipModel.thirdSetShoot.add(wrappedPositions);
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
