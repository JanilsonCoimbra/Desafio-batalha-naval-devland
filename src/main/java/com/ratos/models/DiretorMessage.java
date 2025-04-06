package com.ratos.models;

import com.ratos.configs.Configs;
import com.ratos.interfaces.EventsEnum;

public class DiretorMessage {
    
    public static Message createMessageSubscription(String correlationId) {
        BuilderMessage builderMessage = new BuilderMessage();
        return builderMessage 
                .setCorrelationId(correlationId)
                .setOrigem(Configs.SUBSCRIPTION_NAME)
                .setEvento(EventsEnum.RegistroNavio)
                .setConteudo("{\"nomeNavio\":\""+Configs.SUBSCRIPTION_NAME+"\",\"posicaoCentral\":{\"x\":"+Configs.POSITION_X+",\"y\":"+Configs.POSITION_Y+"},\"orientacao\":\""+Configs.ORIENTATION+"\"}")
                .build();
    }
}
