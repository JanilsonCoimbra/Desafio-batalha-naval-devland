package com.rats.interfaces;

import java.util.Map;

public interface ICommunication {
    String getCorrelationId();
    void setCorrelationId(String correlationId);

    String getOrigem();
    void setOrigem(String origem);

    EventsEnum getEvento();
    void setEvento(EventsEnum evento);

    String getConteudo();
    void setConteudo(String conteudo);

    Map<String, Integer> getPontuacaoNavios();
    void setPontuacaoNavios(Map<String, Integer> pontuacaoNavios);

    String getNavioDestino();
    void setNavioDestino(String navioDestino);

}