package com.rats.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class JsonContentBuilder {
     private final Map<String, Object> content ;

    public JsonContentBuilder() {
        this.content = new LinkedHashMap<>();
    }

    public JsonContentBuilder addField(String key, Object value) {
        this.content.put(key, value);
        return this;
    }

    public Map<String, Object> build() {
        return this.content;
    }
}
