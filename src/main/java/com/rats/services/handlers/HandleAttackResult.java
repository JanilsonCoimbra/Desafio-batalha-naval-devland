package com.rats.services.handlers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rats.configs.Configs;
import com.rats.interfaces.EventsEnum;
import com.rats.interfaces.ICommunication;
import com.rats.interfaces.IHandleChain;
import com.rats.models.AttackResultContent;
import com.rats.models.DirectorMessage;
import com.rats.models.Message;
import com.rats.services.ServiceBus;

public class HandleAttackResult implements IHandleChain {


    private IHandleChain nextHandler;
    
        @Override
        public IHandleChain next(IHandleChain nextHandler) {
            this.nextHandler = nextHandler;
            return this.nextHandler;
        }
    
        @Override
        public ICommunication validate(ICommunication request) {

            if (request.getEvento() == EventsEnum.ResultadoAtaqueEfetuado && request.getNavioDestino().equals(Configs.SUBSCRIPTION_NAME)) {
                
                System.out.println("------------------------------------------------------------");
                System.out.println("Atack Result: Processing message.");
                System.out.println("------------------------------------------------------------");  

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL);
                
                try {
                    AttackResultContent messageReceived = objectMapper.readValue(request.getConteudo(), AttackResultContent.class);
                    System.out.println("Distancia aproximada: " + messageReceived.getDistanciaAproximada());

                    if (messageReceived.getDistanciaAproximada() <= 7) {
                        Configs.SHOOT_LEVEL = 1;

                        System.out.println("Distancia menor que 7: " + messageReceived.getDistanciaAproximada());

                        Configs.DISTANCE_APPROXIMATE = String.valueOf(messageReceived.getDistanciaAproximada());
                        List<Long[]> wrappedPositions = new ArrayList<>();
                        calcularPosicoesPossiveis(messageReceived.getPosicao().getX(), messageReceived.getPosicao().getY(), messageReceived.getDistanciaAproximada())
                            .forEach(pos -> wrappedPositions.add(Arrays.stream(pos).boxed().toArray(Long[]::new)));
                        Configs.SECOND_SET_SHOOT.add(wrappedPositions);

                        System.out.println("Posicoes possiveis: " + wrappedPositions);
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

        private boolean isGoodShoot(String result) {
            // TODO: Se distancia aproximada != de 1000 true
            return false;
        }

        public static List<long[]> calcularPosicoesPossiveis(long x, long y, double raio) {
        List<long[]> posicoes = new ArrayList<>();
        long raioInt = Math.round(raio);
        // Percorrer todas as posições dentro do raio
        for (long i = -raioInt; i <= raio; i++) {
            for (long j = -raioInt; j <= raio; j++) {
                long novoX = x + j;
                long novoY = y + i;
                
                double distanciaTeste = Math.round(Math.sqrt((double) (i * i + j * j)) * 100.0) / 100.0;
                double raioArredondado = Math.round(raio * 100.0) / 100.0;
                // Verificar se a posição está dentro dos limites da matriz
                if (novoX >= 0 && novoX < 30 && novoY >= 0 && novoY < 100) {
                    // Verificar se a posição está dentro do raio
                    if (distanciaTeste == raioArredondado) {
                        posicoes.add(new long[]{novoX, novoY});
                    }
                }
            }
        }

        return posicoes;
    }
}
