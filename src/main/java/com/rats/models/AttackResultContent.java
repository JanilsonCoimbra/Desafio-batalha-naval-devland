package com.rats.models;

public class AttackResultContent {

    private Posicao posicao;
    private boolean acertou;
    private double distanciaAproximada;

    // Construtor padrão
    public AttackResultContent() {
    }

    // Getters e Setters
    public Posicao getPosicao() {
        return posicao;
    }

    public void setPosicao(Posicao posicao) {
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

    // Classe interna para representar a posição
    public static class Posicao {
        private Long x;
        private Long y;

        public Posicao() {
        }

        public Long getX() {
            return x;
        }

        public void setX(Long x) {
            this.x = x;
        }

        public Long getY() {
            return y;
        }

        public void setY(Long y) {
            this.y = y;
        }
    }
}