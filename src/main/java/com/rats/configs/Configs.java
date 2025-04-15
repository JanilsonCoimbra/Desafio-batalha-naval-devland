package com.rats.configs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rats.interfaces.ShipOrientation;

public class Configs {
    public static final String CONNECTION_STRING = "Endpoint=sb://servdevland.servicebus.windows.net/;SharedAccessKeyName=casaratolandia;SharedAccessKey=MUt2vhyqM/TwWxhad+DzI2L1wjyifG3wP+ASbPh+dYc=";
    public static final String TOPIC_NAME = "desafio.batalha_naval.casaratolandia";
    public static final String SUBSCRIPTION_NAME = "rato_do_mar";
    public static final List<List<Object>> SHIP_LIST_POSITION = new ArrayList<>();

    public static String POSITION_Y;
    public static String POSITION_X;
    public static String ORIENTATION;
    public static final String CRIPTOGRAFY_KEY_STRING;
    public static final boolean ACTIVATE_LOG = true;
    
    public static final List<List<Integer>> FIRST_SET_SHOOT = new ArrayList<>();
    static {
        CRIPTOGRAFY_KEY_STRING = "crta55898a4r4136fbge2ef6315a1268";

        SHIP_LIST_POSITION.add(List.of(15, 1, ShipOrientation.HORIZONTAL.getOrientation()));
        SHIP_LIST_POSITION.add(List.of(15, 16, ShipOrientation.HORIZONTAL.getOrientation()));
        SHIP_LIST_POSITION.add(List.of(15, 30, ShipOrientation.HORIZONTAL.getOrientation()));
        SHIP_LIST_POSITION.add(List.of(13, 15, ShipOrientation.VERTICAL.getOrientation()));

        SHIP_LIST_POSITION.add(List.of(30, 1, ShipOrientation.HORIZONTAL.getOrientation()));
        SHIP_LIST_POSITION.add(List.of(30, 16, ShipOrientation.HORIZONTAL.getOrientation()));
        SHIP_LIST_POSITION.add(List.of(30, 30, ShipOrientation.HORIZONTAL.getOrientation()));
        SHIP_LIST_POSITION.add(List.of(28, 15, ShipOrientation.VERTICAL.getOrientation()));

        SHIP_LIST_POSITION.add(List.of(45, 1, ShipOrientation.HORIZONTAL.getOrientation()));
        SHIP_LIST_POSITION.add(List.of(45, 16, ShipOrientation.HORIZONTAL.getOrientation()));
        SHIP_LIST_POSITION.add(List.of(45, 30, ShipOrientation.HORIZONTAL.getOrientation()));
        SHIP_LIST_POSITION.add(List.of(43, 15, ShipOrientation.VERTICAL.getOrientation()));

        SHIP_LIST_POSITION.add(List.of(60, 1, ShipOrientation.HORIZONTAL.getOrientation()));
        SHIP_LIST_POSITION.add(List.of(60, 16, ShipOrientation.HORIZONTAL.getOrientation()));
        SHIP_LIST_POSITION.add(List.of(60, 30, ShipOrientation.HORIZONTAL.getOrientation()));
        SHIP_LIST_POSITION.add(List.of(58, 15, ShipOrientation.VERTICAL.getOrientation()));

        SHIP_LIST_POSITION.add(List.of(75, 1, ShipOrientation.HORIZONTAL.getOrientation()));
        SHIP_LIST_POSITION.add(List.of(75, 16, ShipOrientation.HORIZONTAL.getOrientation()));
        SHIP_LIST_POSITION.add(List.of(75, 30, ShipOrientation.HORIZONTAL.getOrientation()));
        SHIP_LIST_POSITION.add(List.of(73, 15, ShipOrientation.VERTICAL.getOrientation()));

        SHIP_LIST_POSITION.add(List.of(90, 1, ShipOrientation.HORIZONTAL.getOrientation()));
        SHIP_LIST_POSITION.add(List.of(90, 16, ShipOrientation.HORIZONTAL.getOrientation()));
        SHIP_LIST_POSITION.add(List.of(90, 30, ShipOrientation.HORIZONTAL.getOrientation()));
        SHIP_LIST_POSITION.add(List.of(88, 15, ShipOrientation.VERTICAL.getOrientation()));

        FIRST_SET_SHOOT.add(Arrays.asList(94, 24));
        FIRST_SET_SHOOT.add(Arrays.asList(83, 24));
        FIRST_SET_SHOOT.add(Arrays.asList(72, 24));
        FIRST_SET_SHOOT.add(Arrays.asList(61, 24));
        FIRST_SET_SHOOT.add(Arrays.asList(50, 24));
        FIRST_SET_SHOOT.add(Arrays.asList(39, 24));
        FIRST_SET_SHOOT.add(Arrays.asList(28, 24));
        FIRST_SET_SHOOT.add(Arrays.asList(17, 24));
        FIRST_SET_SHOOT.add(Arrays.asList(6, 24));
        FIRST_SET_SHOOT.add(Arrays.asList(94, 11));
        FIRST_SET_SHOOT.add(Arrays.asList(83, 11));
        FIRST_SET_SHOOT.add(Arrays.asList(72, 11));
        FIRST_SET_SHOOT.add(Arrays.asList(61, 11));
        FIRST_SET_SHOOT.add(Arrays.asList(50, 11));
        FIRST_SET_SHOOT.add(Arrays.asList(39, 11));
        FIRST_SET_SHOOT.add(Arrays.asList(28, 11));
        FIRST_SET_SHOOT.add(Arrays.asList(17, 11));
        FIRST_SET_SHOOT.add(Arrays.asList(6, 11));
        FIRST_SET_SHOOT.add(Arrays.asList(94, 2));
        FIRST_SET_SHOOT.add(Arrays.asList(83, 2));
        FIRST_SET_SHOOT.add(Arrays.asList(72, 2));
        FIRST_SET_SHOOT.add(Arrays.asList(61, 2));
        FIRST_SET_SHOOT.add(Arrays.asList(50, 2));
        FIRST_SET_SHOOT.add(Arrays.asList(39, 2));
        FIRST_SET_SHOOT.add(Arrays.asList(28, 2));
        FIRST_SET_SHOOT.add(Arrays.asList(17, 2));
        FIRST_SET_SHOOT.add(Arrays.asList(6, 2));
    }
    public static List<Integer> FIRST_SET_SHOOT_FIVE;
    public static List<List<Integer>> SECOND_SET_SHOOT;
    public static List<List<Integer>> THIRD_SET_SHOOT;
    public static Integer enemyScore;

    private Configs() {}
}
