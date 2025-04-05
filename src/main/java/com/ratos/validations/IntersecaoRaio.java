package com.ratos.validations;

import java.util.HashSet;
import java.util.Set;
import java.awt.Point;

public class IntersecaoRaio {
    public void getIntercessionPoint(String[] args) {
        // Coordenadas dos disparos
        Point disparo1 = new Point(5, 5);
        Point disparo2 = new Point(8, 8);

        // Raio de 7 posições
        int raio = 7;

        // Calcula as áreas de proximidade
        Set<Point> area1 = calcularArea(disparo1, raio);
        Set<Point> area2 = calcularArea(disparo2, raio);

        // Calcula a interseção
        area1.retainAll(area2);

        // Exibe os pontos de interseção
        System.out.println("Pontos de interseção:");
        for (Point ponto : area1) {
            System.out.println(ponto);
        }
    }

    private static Set<Point> calcularArea(Point centro, int raio) {
        Set<Point> area = new HashSet<>();
        for (int x = -raio; x <= raio; x++) {
            for (int y = -raio; y <= raio; y++) {
                if (x * x + y * y <= raio * raio) {
                    area.add(new Point(centro.x + x, centro.y + y));
                }
            }
        }
        return area;
    }
}
