package com.rats.validations;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonValidate {

    public static void isValidJson(String json) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readTree(json);
        } catch (Exception e) {
            throw new Exception("Invalid JSON format: " + e.getMessage(), e);
        }
    }
}
