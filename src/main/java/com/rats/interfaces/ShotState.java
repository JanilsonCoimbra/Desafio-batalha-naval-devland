package com.rats.interfaces;

import java.util.Arrays;
import java.util.List;

import com.azure.messaging.servicebus.ServiceBusMessage;
import com.rats.models.DirectorMessage;
import com.rats.models.Message;
import com.rats.services.ServiceBus;

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
}
