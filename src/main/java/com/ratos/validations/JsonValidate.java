package com.ratos.validations;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonValidate {

    public static boolean isValidJson(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readTree(json); // Tenta parsear o JSON
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
