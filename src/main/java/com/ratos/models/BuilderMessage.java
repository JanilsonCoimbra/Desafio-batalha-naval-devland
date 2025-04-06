package com.ratos.models;

import java.util.Map;

import com.ratos.interfaces.EventsEnum;

public class BuilderMessage {
    
    Message message = new Message();

    
    public BuilderMessage setCorrelationId(String correlationId) {
        message.setCorrelationId(correlationId);
        return this;
    }

    public BuilderMessage setOrigem(String origem) {
        message.setOrigem(origem);
        return this;
    }

    public BuilderMessage setNavioDestino(String navioDestino) {
        message.setNavioDestino(navioDestino);
        return this;
    }

    public BuilderMessage setPontuacaoNavios(Map<String, Integer> pontuacaoNavios) {
        message.setPontuacaoNavios(pontuacaoNavios);
        return this;
    }

    public BuilderMessage setEvento(EventsEnum evento) {
        message.setEvento(evento);
        return this;
    }

    public BuilderMessage setConteudo(Object conteudo) {
        message.setConteudo(conteudo);
        return this;
    }

    public Message build() {
        return message;
    }
}
