package com.rats.services.handlers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.rats.configs.Configs;
import com.rats.configs.HandleLog;
import com.rats.interfaces.EventsEnum;
import com.rats.interfaces.ICommunication;
import com.rats.interfaces.IHandleChain;
import com.rats.models.DirectorMessage;
import com.rats.models.Message;
import com.rats.models.ShipModel;
import com.rats.services.ServiceBus;

public class HandleAttackEnemy implements IHandleChain {


    private IHandleChain nextHandler;
    ShipModel shipModel = ShipModel.getShipModel();
    
        @Override
        public IHandleChain next(IHandleChain nextHandler) {
            this.nextHandler = nextHandler;
            return this.nextHandler;
        }
    
        @Override
        public ICommunication validate(ICommunication request) {


            if (request.getEvento() == EventsEnum.LiberacaoAtaque && request.getNavioDestino().equals(Configs.SUBSCRIPTION_NAME)) {
                HandleLog.title("Atack: Processing message");  

                String correlationId = request.getCorrelationId();
                String message = shoot(correlationId);
                ServiceBusMessage messageService = new ServiceBusMessage(message.toString());


                // messageService.setCorrelationId(message.getCorrelationId());

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

            if (shipModel.getShootLevel() == 0) {
                x_y_try = firstLevelShoot();
            } else if (shipModel.getShootLevel() == 1) {
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
            //List<List<Long[]>> SECOND_SET_SHOOT

            int xAtack = shipModel.secondSetShoot.get(0).get(0)[0].intValue();
            int yAtack = shipModel.secondSetShoot. get(0).get(0)[1].intValue();
            shipModel.getSecondSetShoot().remove(0);
            return Arrays.asList(xAtack, yAtack);
        }

        private List<List<Integer>> setSecondLevelShoot() {
            if (shipModel.getDistanceApproximate() == "1000.0") {
                shipModel.setShootLevel(0);
                return Arrays.asList(Arrays.asList(0, 0));
            } else if (isPerpendicularCase()) {
                shipModel.setShootLevel(1);
                return Arrays.asList(Arrays.asList(0, 0));
            } else if (shipModel.getDistanceApproximate() == "1000.0") {
                shipModel.setShootLevel(2);
                return Arrays.asList(Arrays.asList(0, 0));
            }
            return null;
        }

        private boolean isPerpendicularCase() {
            if (shipModel.getDistanceApproximate() == "1" || 
            shipModel.getDistanceApproximate() == "2" || 
            shipModel.getDistanceApproximate() == "3" || 
            shipModel.getDistanceApproximate() == "4" || 
            shipModel.getDistanceApproximate() == "5" || 
            shipModel.getDistanceApproximate() == "6" || 
            shipModel.getDistanceApproximate() == "7") {
                return true;
            }
            return false;
        }

        private boolean isDiagonalCase() {
            if (shipModel.getDistanceApproximate().contains("1,4") || 
            shipModel.getDistanceApproximate().contains("2,8") || 
            shipModel.getDistanceApproximate().contains("4,2") || 
            shipModel.getDistanceApproximate().contains("5,6")) {
                return true;
            }
            return false;
        }

        private boolean isMediumCase() {
            if (shipModel.getDistanceApproximate().contains("6,7") || 
            shipModel.getDistanceApproximate().contains("6,3") || 
            shipModel.getDistanceApproximate().contains("6,0") || 
            shipModel.getDistanceApproximate().contains("6,4") || 
            shipModel.getDistanceApproximate().contains("5,8") || 
            shipModel.getDistanceApproximate().contains("5,3") || 
            shipModel.getDistanceApproximate() == "5" || 
            shipModel.getDistanceApproximate().contains("4,4") || 
            shipModel.getDistanceApproximate().contains("4,1")) {
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
