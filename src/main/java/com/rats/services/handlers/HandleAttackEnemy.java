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
                // Calibrar para não atirar no próprio barco
                String message = shoot(correlationId);
                ServiceBusMessage messageService = new ServiceBusMessage(message.toString());

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
                } else if (shipModel.getShootLevel() == 2) {
                    x_y_try = thirdLevelShoot();
                }
                HandleLog.title("Atack: Random shot at coordinates: (" + x_y_try.get(0) + ", " + x_y_try.get(1) + ")");
                Message message = DirectorMessage.createAttackMessage(correlationId, x_y_try.get(0).byteValue(), x_y_try.get(1).byteValue());
                return message.toString();
        }

        private List<Integer> firstLevelShoot() {
            try {
                Integer xAtack = Configs.FIRST_SET_SHOOT.get(0).get(0);
                Integer yAtack = Configs.FIRST_SET_SHOOT.get(0).get(1);
                Configs.FIRST_SET_SHOOT.remove(0);
                return Arrays.asList(xAtack, yAtack);
            } catch (Exception e) {
                HandleLog.title("First level shoot: " + e.getMessage());
                throw new RuntimeException("Error in first level shoot");
            }
        }

        private List<Integer> secondLevelShoot() {
            try{
                HandleLog.title("Atack: Second level shoot: " + shipModel.getSecondSetShoot().toString());

                Long xAttackLong = shipModel.secondSetShoot.get(0).get(0)[0];
                Long yAttackLong = shipModel.secondSetShoot.get(0).get(0)[1];

                if (xAttackLong == null || yAttackLong == null) {
                    throw new RuntimeException("Error in second level shoot: xAttack or yAttack is null");
                    
                }
                //Long ton Integer
                Integer xAtack = xAttackLong.intValue();
                Integer yAtack = yAttackLong.intValue();
                shipModel.secondSetShoot.remove(0);
                return Arrays.asList(xAtack, yAtack);
            } catch (Exception e) {
                HandleLog.title("Second level shoot: " + e.getMessage());
                throw new RuntimeException("Error in second level shoot");
            }
        }

        private List<Integer> thirdLevelShoot() {
            try{
                Long xAttackLong = shipModel.getThirdSetShoot().get(0).get(0)[0];
                Long yAttackLong = shipModel.getThirdSetShoot().get(0).get(0)[1];

                if (xAttackLong == null || yAttackLong == null) {
                    throw new RuntimeException("Error in third level shoot: xAttack or yAttack is null");
                }
                Integer xAttack = xAttackLong.intValue();
                Integer yAttack = yAttackLong.intValue();

                System.out.println("Atack: Third level shoot: " + xAttack + ", " + yAttack);
                

                shipModel.thirdSetShootRemove(0);
                return Arrays.asList(xAttack, yAttack);
            } catch (Exception e) {
                HandleLog.title("Third level shoot: " + e.getMessage());
                throw new RuntimeException("Error in third level shoot");
            }
        }
}
