package com.rats.models;
import java.util.logging.Logger;

import com.rats.configs.Configs;
import com.rats.interfaces.EventsEnum;
import com.rats.validations.CalculadoraDeBatalha;

public class DirectorMessage {

    private DirectorMessage() {
    }
    
    public static Message createRegisterMessage(String correlationId) {
        boolean isValidPosition = CalculadoraDeBatalha.isValidPosition(Byte.parseByte(Configs.POSITION_X), Byte.parseByte(Configs.POSITION_Y));
        if (!isValidPosition) {
            Logger.getLogger(DirectorMessage.class.getName()).warning("Posicao invalida, por favor escolha outra posicao.");
            return null;
        }
        BuilderMessage builderMessage = new BuilderMessage();
        return builderMessage 
                .setCorrelationId(correlationId)
                .setOrigem(Configs.SUBSCRIPTION_NAME)
                .setEvento(EventsEnum.RegistroNavio)
                .setConteudo("{\"nomeNavio\":\""+Configs.SUBSCRIPTION_NAME+"\",\"posicaoCentral\":{\"x\":"+Configs.POSITION_X+",\"y\":"+Configs.POSITION_Y+"},\"orientacao\":\""+Configs.ORIENTATION+"\"}")
                .build();
    }


    public static Message createAttackMessage(String correlationId, byte positionX, byte positionY) {
        boolean isValidPosition = CalculadoraDeBatalha.isValidPosition(positionX, positionY);
        boolean isPossitionMyShip = CalculadoraDeBatalha.isPossitionMyShip(positionX, positionY, Byte.parseByte(Configs.POSITION_X), Byte.parseByte(Configs.POSITION_Y), Configs.ORIENTATION);
        
        if(!isValidPosition || isPossitionMyShip) {
            Logger.getLogger(DirectorMessage.class.getName()).warning("Posição inválida ou posição do meu navio, por favor escolha outra posição.");
            positionX = 1;
            positionY = 1;
        }


        BuilderMessage builderMessage = new BuilderMessage();
        return builderMessage 
                .setCorrelationId(correlationId)
                .setOrigem(Configs.SUBSCRIPTION_NAME)
                .setEvento(EventsEnum.Ataque)
                .setConteudo("{\"nomeNavio\":\""+Configs.SUBSCRIPTION_NAME+"\",\"posicaoAtaque\":{\"x\":"+positionX+",\"y\":"+positionY+"}}")
                .build();
    }

    public static Message createAttackFakeMessage() {
        BuilderMessage builderMessage = new BuilderMessage();
        return builderMessage 
                .setCorrelationId("fake")
                .setOrigem(Configs.SUBSCRIPTION_NAME)
                .setEvento(EventsEnum.Ataque)
                .setConteudo("{\"nomeNavio\":\""+Configs.SUBSCRIPTION_NAME+"\",\"posicaoAtaque\":{\"x\":"+"-1"+",\"y\":"+"10"+"}}")
                .build();
    }
}


