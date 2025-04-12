package com.rats.interfaces;

public enum ShipOrientation {
    HORIZONTAL("horizontal"),
    VERTICAL("vertical");

    private final String orientation;

     ShipOrientation(String orientation) {
        this.orientation = orientation;
    }

    public String getOrientation() {
        return orientation;
    }   
}