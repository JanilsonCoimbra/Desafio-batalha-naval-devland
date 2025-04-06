package com.rats.models;

public class ShipState {
    private static ShipState instance;

    private final String currentPosition;
    private final String shipName;

    private ShipState() {
        this.currentPosition = "203,25";
        this.shipName = "rato_do_mar";
    }

    public static synchronized ShipState getInstance() {
        if (instance == null) {
            instance = new ShipState();
        }
        return instance;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public String getShipName() {
        return shipName;
    }

    public void atackEnemy(String enemyShipName) {
        System.out.println("Atacando o navio inimigo: " + enemyShipName);
    }

    public void testCrypto(String message) {
        System.out.println("Testando criptografia com a mensagem: " + message);
    }

    public void respondToCryptoTest(String response) {
        System.out.println("Respondendo ao teste de criptografia com: " + response);
    }

    public void registerAgain(String shipName) {
        System.out.println("Registrando novamente o navio: " + shipName);
    }

    public void victory() {
        System.out.println("Vitória!");
    }

    public void shipAbated(String enemyShipName) {
        System.out.println("Navio abatido: " + enemyShipName);
    }

    public void victory(String winnerShipName) {
        System.out.println("Vitória do navio: " + winnerShipName);
    }

    public void startGame() {
        System.out.println("Jogo iniciado!");
    }

    public void attackResult(String result) {
        System.out.println("Resultado do ataque: " + result);
    }

}
