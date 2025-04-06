package com.rats.models;
import com.rats.interfaces.EventsEnum;
import com.rats.interfaces.ICommunication;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class Message implements ICommunication {
    private String correlationId;
    private String origem;
    private String navioDestino;
    private Map<String, Integer> pontuacaoNavios; // Alterado para Map<String, Integer>
    private EventsEnum evento;
    private Object conteudo;

    @Override
    public String getCorrelationId() {
        return correlationId;
    }

    @Override
    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    @Override
    public String getOrigem() {
        return origem;
    }

    @Override
    public void setOrigem(String origem) {
        this.origem = origem;
    }

    @Override
    public EventsEnum getEvento() {
        return evento;
    }

    @Override
    public void setEvento(EventsEnum evento) {
        this.evento = evento;
    }

    @Override
    public Object getConteudo() {
        return conteudo;
    }

    @Override
    public void setConteudo(Object conteudo) {
        this.conteudo = conteudo;
    }

    @Override
    public Map<String, Integer> getPontuacaoNavios() {
        return pontuacaoNavios;
    }

    @Override
    public void setPontuacaoNavios(Map<String, Integer> pontuacaoNavios) {
        this.pontuacaoNavios = pontuacaoNavios;
    }

    @Override
    public String getNavioDestino() {
        return navioDestino;
    }

    @Override
    public void setNavioDestino(String navioDestino) {
        this.navioDestino = navioDestino;
    }

    private String formatConteudo() {
        try {
            if (conteudo instanceof String) {
                return (String) conteudo;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(conteudo);
        } catch (Exception e) {
            return "{}";
        }
    }

    @Override
    public String toString() {
        return "{" +
        "\"correlationId\":\"" + correlationId + "\"," +
        "\"origem\":\"" + origem + "\"," +
        "\"evento\":\"" + (evento != null ? evento.name() : null) + "\"," +
        "\"conteudo\":\"" + formatConteudo().replace("\"", "\\\"") + "\"," +
        "\"pontuacaoNavios\":" + (pontuacaoNavios != null ? pontuacaoNavios.toString() : "{}") +
        "}";
    }
}
