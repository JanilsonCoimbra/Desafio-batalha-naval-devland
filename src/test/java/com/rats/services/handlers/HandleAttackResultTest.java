package com.rats.services.handlers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rats.configs.Configs;
import com.rats.interfaces.EventsEnum;
import com.rats.models.Message;
import com.rats.models.ShipModel;

public class HandleAttackResultTest {
    
    private ShipModel shipModel;
    private Message message;

    @BeforeEach
    public void setUp() {
        shipModel = ShipModel.getShipModel();
        message = new Message();
        message.setEvento(EventsEnum.ResultadoAtaqueEfetuado);
        message.setNavioDestino(Configs.getInstance().SUBSCRIPTION_NAME);
        message.setConteudo("{\"distanciaAproximada\": 5, \"posicao\": {\"x\": 10, \"y\": 20}}");
    }
   
    @Test
    public void testHandleAttackResult() {
        HandleAttackResult handler = new HandleAttackResult(shipModel);
        assertDoesNotThrow(() -> handler.validate(message));
    }
}
