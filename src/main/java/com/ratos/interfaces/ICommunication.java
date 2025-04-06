package com.ratos.interfaces;

import java.util.Map;

public interface ICommunication {
    String getCorrelationId();
    void setCorrelationId(String correlationId);

    String getOrigem();
    void setOrigem(String origem);

    EventsEnum getEvento();
    void setEvento(EventsEnum evento);

    Object getConteudo();
    void setConteudo(Object conteudo);

    Map<String, Integer> getPontuacaoNavios();
    void setPontuacaoNavios(Map<String, Integer> pontuacaoNavios);

    String getNavioDestino();
    void setNavioDestino(String navioDestino);

}