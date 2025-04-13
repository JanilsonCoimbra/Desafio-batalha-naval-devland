package com.rats.validations;

import java.util.ArrayList;
import java.util.List;
public class CalculadoraDeBatalha {

    public static List<long[]> calcularPosicoesPossiveis(long x, long y, double raio) {
    System.out.println("Calculando posições possíveis para o ataque...");
        List<long[]> posicoes = new ArrayList<>();
        long raioInt = Math.round(raio);
        // Percorrer todas as posições dentro do raio
        for (long i = -raioInt; i <= raio; i++) {
            for (long j = -raioInt; j <= raio; j++) {
                long novoX = x + j;
                long novoY = y + i;
                
                double distanciaTeste = Math.round(Math.sqrt((double) (i * i + j * j)) * 100.0) / 100.0;
                double raioArredondado = Math.round(raio * 100.0) / 100.0;
                // Verificar se a posição está dentro dos limites da matriz
                if (novoX >= 0 && novoX < 100 && novoY >= 0 && novoY < 30) {
                    // Verificar se a posição está dentro do raio
                    if (distanciaTeste == raioArredondado) {
                        posicoes.add(new long[]{novoX, novoY});
                    }
                }
            }
        }
        return posicoes;
    }

    public static boolean  isValidPosition(long x, long y) {
        return (x >= 1 && x <= 100 && y >= 1 && y <= 30);
    }

    public static boolean isPossitionMyShip(byte AttackX, byte AttackY, byte positionCentralX, byte positionCentralY, String orientation) {
        byte ponta1DoNavio = 0;
        byte ponta2DoNavio = 0;

        if (orientation.equals("horizontal")) {
            ponta1DoNavio = (byte) (positionCentralX - 2);
            ponta2DoNavio = (byte) (positionCentralX + 2);
        } else if (orientation.equals("vertical")) {
            ponta1DoNavio = (byte) (positionCentralY - 2);
            ponta2DoNavio = (byte) (positionCentralY + 2);
        }

        if(orientation.equals("horizontal") && AttackY == positionCentralY) {
            if (AttackX >= ponta1DoNavio && AttackX <= ponta2DoNavio) {
                return true;
            }
        } else if(orientation.equals("vertical") && AttackX == positionCentralX) {
            if (AttackY >= ponta1DoNavio && AttackY <= ponta2DoNavio) {
                return true;
            }
        }
        return false; 
    }

    private CalculadoraDeBatalha() {
    }
}
