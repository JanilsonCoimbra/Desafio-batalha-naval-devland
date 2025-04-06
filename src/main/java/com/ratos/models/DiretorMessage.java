package com.ratos.models;

import com.ratos.interfaces.EventsEnum;

public class DiretorMessage {
    
    public Message createMessageSubscription(String correlationId, String origem, EventsEnum evento, Object conteudo) {
        BuilderMessage builderMessage = new BuilderMessage();
        return builderMessage 
                .setCorrelationId(correlationId)
                .setOrigem(origem)
                .setEvento(evento)
                .setConteudo("{\"nomeNavio\":\"ratos_do_mar\",\"posicaoCentral\":{\"x\":20,\"y\":20},\"orientacao\":\"vertical\"}")
                .build();
    }
}
