package com.rats.models;

import java.util.ArrayList;
import java.util.List;

import com.rats.configs.Configs;

public class ShipModel {

    private static ShipModel shipModel;
    public final String subscriptionName;
    public final String positionY;
    public final String positionX;
    public final String orientation;
    private Integer shootLevel = 0;
    public String distanceApproximate;
    public List<List<long[]>> secondSetShoot = new ArrayList<>();
    private List<List<long[]>> thirdSetShoot = new ArrayList<>();

    public Position firstSuccessShootPosition;
    // Direita = 0, Esquerda = 1, Cima = 2, Baixo = 3
    public int successDiretion;

    private ShipModel() {
        subscriptionName = Configs.SUBSCRIPTION_NAME;
        positionY = Configs.POSITION_Y;
        positionX = Configs.POSITION_X;
        orientation = Configs.ORIENTATION;
    }

    public static ShipModel getShipModel() {
        if(shipModel == null) {
            shipModel = new ShipModel();
            return shipModel;

        }
        return shipModel;
    }

    public static void setShipModel(ShipModel shipModel) {
        ShipModel.shipModel = shipModel;
    }

    public Integer getShootLevel() {
        return shootLevel;
    }

    public void setShootLevel(Integer shootLevel) {
        this.shootLevel = shootLevel;
    }

    public String getDistanceApproximate() {
        return distanceApproximate;
    }

    public void setDistanceApproximate(String distanceApproximate) {
        this.distanceApproximate = distanceApproximate;
    }

    public List<List<long[]>> getSecondSetShoot() {
        return secondSetShoot;
    }

    public void setSecondSetShoot(List<List<long[]>> secondSetShoot) {
        this.secondSetShoot = secondSetShoot;
    }

    public String getSubscriptionName() {
        return subscriptionName;
    }

    public String getPositionY() {
        return positionY;
    }

    public String getPositionX() {
        return positionX;
    }

    public String getOrientation() {
        return orientation;
    }

    public List<List<long[]>> getThirdSetShoot() {
        return thirdSetShoot;
    }

    public void setThirdSetShoot(List<List<long[]>> thirdSetShoot) {
        if(thirdSetShoot == null) {
            this.thirdSetShoot = new ArrayList<>();
            throw new RuntimeException("Third set shoot is null");
        }
        this.thirdSetShoot.addAll(thirdSetShoot);
    }

    public void thirdSetShootRemove(int index) {
        if(thirdSetShoot == null) {
            this.thirdSetShoot = new ArrayList<>();
            throw new RuntimeException("Third set shoot is null");
        }
        this.thirdSetShoot.remove(index);
    }

    public Position getFirstSuccessShootPosition() {
        return firstSuccessShootPosition;
    }

    public void setFirstSuccessShootPosition(Position firstSuccessShootPosition) {
        this.firstSuccessShootPosition = firstSuccessShootPosition;
    }

    public int getSuccessDiretion() {
        return successDiretion;
    }

    public void setSuccessDiretion(int successDiretion) {
        this.successDiretion = successDiretion;
    }



}
