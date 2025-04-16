package com.rats.interfaces;

import java.util.List;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.rats.configs.Configs;
import com.rats.configs.HandleLog;
import com.rats.models.DirectorMessage;
import com.rats.models.Message;
import com.rats.services.ServiceBus;
import com.rats.services.states.FirstLevelShootException;
import com.rats.validations.CalculadoraDeBatalha;

public abstract class ShotState {
    protected ShotState nextState;

    public void setNextState(ShotState nextState) {
        this.nextState = nextState;
    }

    public abstract void handleShot(String correlationId) throws Exception;

    public void sendMessageShot(int x, int y, String correlationId) {
        Message message = DirectorMessage.createAttackMessage(correlationId, (byte) x, (byte) y);
        ServiceBusMessage messageService = new ServiceBusMessage(message.toString());

        ServiceBus service = ServiceBus.getInstance();
        service.sendMessage(messageService);
    }

    public void shotRandon(String correlationId) throws FirstLevelShootException {
        try {
            HandleLog.title("First level randon attack executed: ");
            List<Integer> randonShot = CalculadoraDeBatalha.generetedAtackPossitionRadon();
            sendMessageShot(randonShot.get(0), randonShot.get(1), correlationId);
        } catch (Exception e) {
            HandleLog.title("First level shoot: " + e.getMessage());
            throw new FirstLevelShootException("Error in first level shoot", e);
        }
    }

    public void shootRedPointAgain(String correlationId) throws FirstLevelShootException {
        try {
            HandleLog.title("First level Red point again attack executed: ");
            sendMessageShot(Configs.POSITION_X_RED_SHOOT, Configs.POSITION_Y_RED_SHOOT, correlationId);
        } catch (Exception e) {
            HandleLog.title("First level shoot: " + e.getMessage());
            throw new FirstLevelShootException("Error in first level shoot", e);
        }
    }
}
