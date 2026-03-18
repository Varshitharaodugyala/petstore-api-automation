package com.veeva.context;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    private final Map<String, Object> context = new HashMap<>();

    public void set(String key, Object value) {
        context.put(key, value);
    }

    public Object get(String key) {
        return context.get(key);
    }

    public long getLong(String key) {
        return ((Number) context.get(key)).longValue();
    }

    public String getString(String key) {
        return (String) context.get(key);
    }

    public int getInt(String key) {
        return ((Number) context.get(key)).intValue();
    }

    public void clear() {
        context.clear();
    }
}

