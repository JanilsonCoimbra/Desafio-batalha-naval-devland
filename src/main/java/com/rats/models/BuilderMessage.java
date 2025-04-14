package com.rats.models;

import java.util.Map;

import com.rats.configs.Configs;
import com.rats.interfaces.EventsEnum;
import com.rats.services.Cryptography;

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

    public BuilderMessage setConteudo(String conteudo) {
        try {
            String messageEncrypted = Cryptography.encryptString(conteudo, Configs.CRIPTOGRAFY_KEY_STRING);
            message.setConteudo(messageEncrypted);
        } catch (Exception e) {
            System.out.println("Error in setConteudo: " + e.getMessage());
        }
        return this;
    }

    public Message build() {
        return message;
    }
}
