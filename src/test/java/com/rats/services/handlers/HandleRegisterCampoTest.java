package com.rats.services.handlers;

import com.rats.RatosDoMarApplication;
import com.rats.interfaces.EventsEnum;
import com.rats.interfaces.ICommunication;
import com.rats.services.ServiceBus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = RatosDoMarApplication.class)
class HandleRegisterCampoTest {

     ServiceBus serviceBus;
     HandleRegisterCampo handler;

    @BeforeEach
    void setUp() {
        serviceBus = mock(ServiceBus.class);
        handler = new HandleRegisterCampo(serviceBus);
    }

    @Test
    void testValidate_CampoLiberadoParaRegistro() {
        
        ICommunication request = mock(ICommunication.class);
        when(request.getEvento()).thenReturn(EventsEnum.CampoLiberadoParaRegistro);

        assertDoesNotThrow(() -> handler.validate(request));
    }
}

