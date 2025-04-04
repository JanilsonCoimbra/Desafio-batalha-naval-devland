package com.ratos.interfaces;

public class IComunication {
    String correlationId; // Guid - Gerado conforme evento
    String origem;
    EventsEnum evento;
    String conteudo;
}