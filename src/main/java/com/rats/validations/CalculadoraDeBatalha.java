package com.rats.validations;

import java.util.ArrayList;
import java.util.List;
import com.rats.configs.Configs;
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
                if (novoX >= 1 && novoX <= 100 && novoY >= 1 && novoY <= 30) {
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

    public static List<Object> generateInitialPositionShip(List<List<Object>> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        int randomIndex = (int) (Math.random() * list.size());

        Configs.SHIP_LIST_POSITION.remove(randomIndex);
        return list.get(randomIndex);
    }

    public static List<Integer> generetedAtackPossitionRadon() {
        List<Integer> list = new ArrayList<>();
        int randomX = (int) (Math.random() * 100) + 1;
        int randomY = (int) (Math.random() * 30) + 1;

        list.add(randomX);
        list.add(randomY);
        return list;  
    }

    public static List<long[]> calculateThirdSetShoot( long x, long y) {
        List<long[]> wrappedPositions = new ArrayList<>();
        boolean janilson = true;
        switch (Configs.enemyScore) {
            case 85, 75, 65:
                wrappedPositions.add(new long[] {x - 2, y});
                wrappedPositions.add(new long[] {x, y - 2});
                wrappedPositions.add(new long[] {x + 2, y});
                wrappedPositions.add(new long[] {x, y + 2});
                break;
            case 55, 45, 35:
                janilson = false;
                if (Configs.POSITION_X_RED_SHOOT != null && Configs.POSITION_X_RED_SHOOT == x) {
                    wrappedPositions.add(new long[] {x - 1, y});
                    wrappedPositions.add(new long[] {x + 1, y});
                    wrappedPositions.add(new long[] {x, y - 1});
                    wrappedPositions.add(new long[] {x, y + 1});
                } else if (Configs.POSITION_Y_RED_SHOOT != null && Configs.POSITION_Y_RED_SHOOT == y) {
                    wrappedPositions.add(new long[] {x, y + 1});
                    wrappedPositions.add(new long[] {x, y - 1});
                    wrappedPositions.add(new long[] {x - 1, y});
                    wrappedPositions.add(new long[] {x + 1, y});
                } else {
                    wrappedPositions.add(new long[] {x - 1, y});
                    wrappedPositions.add(new long[] {x, y - 1});
                    wrappedPositions.add(new long[] {x + 1, y});
                    wrappedPositions.add(new long[] {x, y + 1});
                }
                break;
            default:
                wrappedPositions.add(new long[] {x - 1, y});
                wrappedPositions.add(new long[] {x, y - 1});
                wrappedPositions.add(new long[] {x + 1, y});
                wrappedPositions.add(new long[] {x, y + 1});
                wrappedPositions.add(new long[] {x - 2, y});
                wrappedPositions.add(new long[] {x, y - 2});
                wrappedPositions.add(new long[] {x + 2, y});
                wrappedPositions.add(new long[] {x, y + 2});
                break;
        }

        if (Configs.POSITION_X_RED_SHOOT != null && Configs.POSITION_Y_RED_SHOOT != null && janilson) {
            wrappedPositions.sort((a, b) -> {
                int result = 0;
            
                if (Configs.POSITION_X_RED_SHOOT > x) {
                    if (a[0] < b[0]) return -1;
                    if (a[0] > b[0]) return 1;
                } else if (Configs.POSITION_X_RED_SHOOT < x) {
                    if (a[0] > b[0]) return -1;
                    if (a[0] < b[0]) return 1;
                }
            
                if (Configs.POSITION_Y_RED_SHOOT > y) {
                    if (a[1] < b[1]) return -1;
                    if (a[1] > b[1]) return 1;
                } else if (Configs.POSITION_Y_RED_SHOOT < y) {
                    if (a[1] > b[1]) return -1;
                    if (a[1] < b[1]) return 1;
                }
            
                return result;
            });
        };

        return wrappedPositions;
    }

    private CalculadoraDeBatalha() {
    }
}
