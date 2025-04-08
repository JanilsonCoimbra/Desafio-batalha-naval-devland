package com.rats.validations;

import java.util.ArrayList;
import java.util.List;
public class CalculadoraDeBatalha {

    public static List<long[]> calcularPosicoesPossiveis(long x, long y, double raio) {
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
                if (novoX >= 0 && novoX < 30 && novoY >= 0 && novoY < 100) {
                    // Verificar se a posição está dentro do raio
                    if (distanciaTeste == raioArredondado) {
                        posicoes.add(new long[]{novoX, novoY});
                    }
                }
            }
        }

        return posicoes;
    }

    public boolean isGoodShoot(String result) {
        // TODO: Se distancia aproximada != de 1000 true
        return false;
    }

    private CalculadoraDeBatalha() {
    }
}
