package com.rats.services.handlers;
import com.rats.configs.Configs;
import com.rats.interfaces.EventsEnum;
import com.rats.interfaces.ICommunication;
import com.rats.models.ShipModel;
import com.rats.services.ServiceBus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class HandleAttackEnemyTest {

    private ServiceBus serviceBus;
    private ShipModel shipModel;
    private HandleAttackEnemy handler;

    @BeforeEach
    void setUp() {
        serviceBus = mock(ServiceBus.class);
        shipModel = mock(ShipModel.class);
        handler = new HandleAttackEnemy(serviceBus, shipModel);
    }

    @Test
    void testValidate_LiberacaoAtaque_Event() {
        ICommunication request = mock(ICommunication.class);
        when(request.getEvento()).thenReturn(EventsEnum.LiberacaoAtaque);
        when(request.getNavioDestino()).thenReturn(Configs.getInstance().SUBSCRIPTION_NAME);
        when(request.getCorrelationId()).thenReturn("123");

        assertDoesNotThrow(() -> handler.validate(request));
    }
}