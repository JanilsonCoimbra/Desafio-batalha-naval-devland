package com.rats.models;

import com.rats.configs.Configs;
import com.rats.interfaces.EventsEnum;

public class DirectorMessage {

    private DirectorMessage() {
    }
    
    public static Message createRegisterMessage(String correlationId) {
        BuilderMessage builderMessage = new BuilderMessage();
        return builderMessage 
                .setCorrelationId(correlationId)
                .setOrigem(Configs.SUBSCRIPTION_NAME)
                .setEvento(EventsEnum.RegistroNavio)
                .setConteudo("{\"nomeNavio\":\""+Configs.SUBSCRIPTION_NAME+"\",\"posicaoCentral\":{\"x\":"+Configs.POSITION_X+",\"y\":"+Configs.POSITION_Y+"},\"orientacao\":\""+Configs.ORIENTATION+"\"}")
                .build();
    }
}
