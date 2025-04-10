package com.rats.configs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Configs {
    public static final String CONNECTION_STRING = "Endpoint=sb://servdevland.servicebus.windows.net/;SharedAccessKeyName=casaratolandia;SharedAccessKey=MUt2vhyqM/TwWxhad+DzI2L1wjyifG3wP+ASbPh+dYc=";
    public static final String TOPIC_NAME = "desafio.batalha_naval.casaratolandia";
    public static final String SUBSCRIPTION_NAME = "rato_do_mar";

    public static final String POSITION_Y = "20";
    public static final String POSITION_X = "20";
    public static final String ORIENTATION = "vertical";
    public static final String CRIPTOGRAFY_KEY_STRING;
    public static final boolean ACTIVATE_LOG = true;
    
    public static final List<List<Integer>> FIRST_SET_SHOOT = new ArrayList<>();
    static {
        CRIPTOGRAFY_KEY_STRING = "";
        FIRST_SET_SHOOT.add(Arrays.asList(93, 23));
        FIRST_SET_SHOOT.add(Arrays.asList(82, 23));
        FIRST_SET_SHOOT.add(Arrays.asList(71, 23));
        FIRST_SET_SHOOT.add(Arrays.asList(60, 23));
        FIRST_SET_SHOOT.add(Arrays.asList(49, 23));
        FIRST_SET_SHOOT.add(Arrays.asList(38, 23));
        FIRST_SET_SHOOT.add(Arrays.asList(27, 23));
        FIRST_SET_SHOOT.add(Arrays.asList(16, 23));
        FIRST_SET_SHOOT.add(Arrays.asList(5, 23));
        FIRST_SET_SHOOT.add(Arrays.asList(93, 10));
        FIRST_SET_SHOOT.add(Arrays.asList(82, 10));
        FIRST_SET_SHOOT.add(Arrays.asList(71, 10));
        FIRST_SET_SHOOT.add(Arrays.asList(60, 10));
        FIRST_SET_SHOOT.add(Arrays.asList(49, 10));
        FIRST_SET_SHOOT.add(Arrays.asList(38, 10));
        FIRST_SET_SHOOT.add(Arrays.asList(27, 10));
        FIRST_SET_SHOOT.add(Arrays.asList(16, 10));
        FIRST_SET_SHOOT.add(Arrays.asList(5, 10));
        FIRST_SET_SHOOT.add(Arrays.asList(93, 1));
        FIRST_SET_SHOOT.add(Arrays.asList(82, 1));
        FIRST_SET_SHOOT.add(Arrays.asList(71, 1));
        FIRST_SET_SHOOT.add(Arrays.asList(60, 1));
        FIRST_SET_SHOOT.add(Arrays.asList(49, 1));
        FIRST_SET_SHOOT.add(Arrays.asList(38, 1));
        FIRST_SET_SHOOT.add(Arrays.asList(27, 1));
        FIRST_SET_SHOOT.add(Arrays.asList(16, 1));
        FIRST_SET_SHOOT.add(Arrays.asList(5, 1));
    }
    public static List<List<Integer>> SECOND_SET_SHOOT;
    public static List<List<Integer>> THIRD_SET_SHOOT;

    private Configs() {}
}
