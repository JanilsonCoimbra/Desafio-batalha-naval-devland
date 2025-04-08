package com.rats.models;
import com.rats.interfaces.EventsEnum;
import com.rats.interfaces.ICommunication;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class Message implements ICommunication {
    private String correlationId;
    private String origem;
    private String navioDestino;
    private Map<String, Integer> pontuacaoNavios;
    private EventsEnum evento;
    private String conteudo;

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
    public String getConteudo() {
        return conteudo;
    }

    @Override
    public void setConteudo(String conteudo) {
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
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        if (correlationId != null) {
            sb.append("\"correlationId\":\"").append(correlationId).append("\",");
        }
        if (origem != null) {
            sb.append("\"origem\":\"").append(origem).append("\",");
        }
        if (navioDestino != null) {
            sb.append("\"navioDestino\":\"").append(navioDestino).append("\",");
        }
        if (evento != null) {
            sb.append("\"evento\":\"").append(evento.name()).append("\",");
        }
        if (conteudo != null) {
            sb.append("\"conteudo\":\"").append(formatConteudo().replace("\"", "\\\"")).append("\",");
        }
        if (pontuacaoNavios != null) {
            sb.append("\"pontuacaoNavios\":").append(pontuacaoNavios.toString()).append(",");
        }

        if (sb.charAt(sb.length() - 1) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }

        sb.append("}");
        return sb.toString();
    }
}
