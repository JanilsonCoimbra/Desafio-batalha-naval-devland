package com.rats.configs;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Configs {
    private static final Configs INSTANCE = new Configs();

    public static Configs getInstance() {
        return INSTANCE;
    }

    public String CONNECTION_STRING;
    public String TOPIC_NAME;
    public String SUBSCRIPTION_NAME;
    public final String POSITION_Y = "20";
    public final String POSITION_X = "20";
    public final String ORIENTATION = "vertical";
    public final String CRIPTOGRAFY_KEY_STRING = "";

    public final static List<List<Integer>> FIRST_SET_SHOOT = new ArrayList<>();

    static {
        Properties props = new Properties();
        try (InputStream input = Configs.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                props.load(input);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println(
            "Loading properties from application.properties file: " + props.getProperty("AZURE_SERVICE_BUS_CONNECTION_STRING") +
            ", " + props.getProperty("TOPIC_NAME") + ", " + props.getProperty("SUBSCRIPTION_NAME")
        );
        INSTANCE.CONNECTION_STRING = props.getProperty("AZURE_SERVICE_BUS_CONNECTION_STRING");
        INSTANCE.TOPIC_NAME = props.getProperty("TOPIC_NAME");
        INSTANCE.SUBSCRIPTION_NAME = props.getProperty("SUBSCRIPTION_NAME");
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
}
