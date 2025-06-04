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
                .setOrigem(Configs.getInstance().SUBSCRIPTION_NAME)
                .setEvento(EventsEnum.RegistroNavio)
                .setConteudo("{\"nomeNavio\":\""+Configs.getInstance().SUBSCRIPTION_NAME+"\",\"posicaoCentral\":{\"x\":"+Configs.getInstance().POSITION_X+",\"y\":"+Configs.getInstance().POSITION_Y+"},\"orientacao\":\""+Configs.getInstance().ORIENTATION+"\"}")
                .build();
    }


    public static Message createAttackMessage(String correlationId, byte positionX, byte positionY) {
        BuilderMessage builderMessage = new BuilderMessage();
        return builderMessage 
                .setCorrelationId(correlationId)
                .setOrigem(Configs.getInstance().SUBSCRIPTION_NAME)
                .setEvento(EventsEnum.Ataque)
                .setConteudo("{\"nomeNavio\":\""+Configs.getInstance().SUBSCRIPTION_NAME+"\",\"posicaoAtaque\":{\"x\":"+positionX+",\"y\":"+positionY+"}}")
                .build();
    }

    public static Message createAttackFakeMessage() {
        BuilderMessage builderMessage = new BuilderMessage();
        return builderMessage 
                .setCorrelationId("fake")
                .setOrigem(Configs.getInstance().SUBSCRIPTION_NAME)
                .setEvento(EventsEnum.Ataque)
                .setConteudo("{\"nomeNavio\":\""+Configs.getInstance().SUBSCRIPTION_NAME+"\",\"posicaoAtaque\":{\"x\":"+"-1"+",\"y\":"+"10"+"}}")
                .build();
    }
}


