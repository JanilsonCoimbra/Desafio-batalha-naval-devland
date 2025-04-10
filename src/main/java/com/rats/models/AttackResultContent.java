package com.rats.models;

public class AttackResultContent {

    private Position posicao;
    private boolean acertou;
    private double distanciaAproximada;

    // Construtor padrão
    public AttackResultContent() {
    }

    // Getters e Setters
    public Position getPosicao() {
        return posicao;
    }

    public void setPosicao(Position posicao) {
        this.posicao = posicao;
    }

    public boolean isAcertou() {
        return acertou;
    }

    public void setAcertou(boolean acertou) {
        this.acertou = acertou;
    }

    public double getDistanciaAproximada() {
        return distanciaAproximada;
    }

    public void setDistanciaAproximada(double distanciaAproximada) {
        this.distanciaAproximada = distanciaAproximada;
    }
}
