package com.rats.services.handlers;
import java.util.Arrays;
import java.util.List;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private final ServiceBus serviceBus;
    private final ShipModel shipModel;

    public HandleAttackEnemy(ServiceBus serviceBus, ShipModel shipModel) {
        this.serviceBus = serviceBus;
        this.shipModel = shipModel;
    }

    private IHandleChain nextHandler;

    
        @Override
        public IHandleChain next(IHandleChain nextHandler) {
            this.nextHandler = nextHandler;
            return this.nextHandler;
        }
    
        @Override
        public ICommunication validate(ICommunication request) {


            try{
                if (request.getEvento() == EventsEnum.LiberacaoAtaque && request.getNavioDestino().equals(Configs.getInstance().SUBSCRIPTION_NAME)) {
                    HandleLog.title("Atack: Processing message");

                    String correlationId = request.getCorrelationId();
                    String message = shoot(correlationId);
                    ServiceBusMessage messageService = new ServiceBusMessage(message.toString());
    
                    serviceBus.sendMessage(messageService);
                }

            } catch (Exception e) {
                HandleLog.title("Error in HandleAttackEnemy: " + e.getMessage());
            }

            if (nextHandler != null) {
                return nextHandler.validate(request);

            }
            
            return request;
        }

        private String shoot(String correlationId) throws JsonProcessingException {
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
            Integer xAtack = Configs.getInstance().FIRST_SET_SHOOT.get(0).get(0);
            Integer yAtack = Configs.getInstance().FIRST_SET_SHOOT.get(0).get(1);
            Configs.getInstance().FIRST_SET_SHOOT.remove(0);
            return Arrays.asList(xAtack, yAtack);
        }

        private List<Integer> secondLevelShoot() {
            //List<List<Long[]>> SECOND_SET_SHOOT

            int xAtack = shipModel.secondSetShoot.get(0).get(0)[0].intValue();
            int yAtack = shipModel.secondSetShoot. get(0).get(0)[1].intValue();
            shipModel.getSecondSetShoot().remove(0);
            return Arrays.asList(xAtack, yAtack);
        }
    }
