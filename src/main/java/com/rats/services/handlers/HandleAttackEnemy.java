package com.rats.services.handlers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                
                String correlationId = request.getCorrelationId();
                String message = shoot(correlationId);
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

        private String shoot(String correlationId) {
            List<Integer> x_y_try = Arrays.asList(1, 1);

            if (Configs.SHOOT_LEVEL == 0) {
                x_y_try = firstLevelShoot();
            } else if (Configs.SHOOT_LEVEL == 1) {
                x_y_try = secondLevelShoot();
            }

            System.out.println("------------------------------------------------------------");
            System.out.println("Atack: Random shot at coordinates: (" + x_y_try.get(0) + ", " + x_y_try.get(1) + ")");
            Message message = DirectorMessage.createAttackMessage(correlationId, x_y_try.get(0).byteValue(), x_y_try.get(1).byteValue());
            System.out.println("------------------------------------------------------------");
            return message.toString();
        }

        private List<Integer> firstLevelShoot() {
            Integer xAtack = Configs.FIRST_SET_SHOOT.get(0).get(0);
            Integer yAtack = Configs.FIRST_SET_SHOOT.get(0).get(1);
            Configs.FIRST_SET_SHOOT.remove(0);
            return Arrays.asList(xAtack, yAtack);
        }

        private List<Integer> secondLevelShoot() {
            Integer xAtack = Configs.SECOND_SET_SHOOT.get(0).get(0);
            Integer yAtack = Configs.SECOND_SET_SHOOT.get(0).get(1);
            Configs.SECOND_SET_SHOOT.remove(0);
            return Arrays.asList(xAtack, yAtack);
        }

        private List<List<Integer>> setSecondLevelShoot() {
            if (Configs.DISTANCE_APPROXIMATE == "1000.0") {
                Configs.SHOOT_LEVEL = 0;
                return Arrays.asList(Arrays.asList(0, 0));
            } else if (isPerpendicularCase()) {
                Configs.SHOOT_LEVEL = 1;
                return Arrays.asList(Arrays.asList(0, 0));
            } else if (Configs.DISTANCE_APPROXIMATE == "1000.0") {
                Configs.SHOOT_LEVEL = 2;
                return Arrays.asList(Arrays.asList(0, 0));
            }
            return null;
        }

        private boolean isPerpendicularCase() {
            if (Configs.DISTANCE_APPROXIMATE == "1" || 
            Configs.DISTANCE_APPROXIMATE == "2" || 
            Configs.DISTANCE_APPROXIMATE == "3" || 
            Configs.DISTANCE_APPROXIMATE == "4" || 
            Configs.DISTANCE_APPROXIMATE == "5" || 
            Configs.DISTANCE_APPROXIMATE == "6" || 
            Configs.DISTANCE_APPROXIMATE == "7") {
                return true;
            }
            return false;
        }

        private boolean isDiagonalCase() {
            if (Configs.DISTANCE_APPROXIMATE.contains("1,4") || 
            Configs.DISTANCE_APPROXIMATE.contains("2,8") || 
            Configs.DISTANCE_APPROXIMATE.contains("4,2") || 
            Configs.DISTANCE_APPROXIMATE.contains("5,6")) {
                return true;
            }
            return false;
        }

        private boolean isMediumCase() {
            if (Configs.DISTANCE_APPROXIMATE.contains("6,7") || 
            Configs.DISTANCE_APPROXIMATE.contains("6,3") || 
            Configs.DISTANCE_APPROXIMATE.contains("6,0") || 
            Configs.DISTANCE_APPROXIMATE.contains("6,4") || 
            Configs.DISTANCE_APPROXIMATE.contains("5,8") || 
            Configs.DISTANCE_APPROXIMATE.contains("5,3") || 
            Configs.DISTANCE_APPROXIMATE == "5" || 
            Configs.DISTANCE_APPROXIMATE.contains("4,4") || 
            Configs.DISTANCE_APPROXIMATE.contains("4,1")) {
                return true;
            }
            return false;
        }

        public static List<long[]> calcularPosicoesPossiveis(long x, long y, double raio) {
            List<long[]> posicoes = new ArrayList<>();
            long raioInt = Math.round(raio);
            // Percorrer todas as posições dentro do raio
            for (long i = -raioInt; i <= raio; i++) {
                for (long j = -raioInt; j <= raio; j++) {
                    long novoX = x + i;
                    long novoY = y + j;
                    
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
