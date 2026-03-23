package com.veeva.context;

import java.util.HashMap;
import java.util.Map;

/*
 * ScenarioContext is used to store and share test data between
 * different Step Definition methods during execution of ONE Cucumber scenario.
 *
 * Example:
 * Step 1 → Create Pet → store petId
 * Step 2 → Get Pet → use stored petId
 *
 * So this class works like a temporary memory (key-value storage)
 * which is cleared after scenario execution.
 */
public class ScenarioContext {

    // Map to store scenario data
    // Key → String (example: "petId")
    // Value → Object (can be Long, String, Response etc.)
    private final Map<String, Object> context = new HashMap<>();

    // Store value in context using key
    public void set(String key, Object value) {
        context.put(key, value);
    }

    // Get stored value using key (generic Object)
    public Object get(String key) {
        return context.get(key);
    }

    // Get stored numeric value as long
    // Used for values like petId, orderId
    public long getLong(String key) {
        return ((Number) context.get(key)).longValue();
    }

    // Get stored value as String
    public String getString(String key) {
        return (String) context.get(key);
    }

    // Get stored numeric value as int
    public int getInt(String key) {
        return ((Number) context.get(key)).intValue();
    }

    // Clear all stored data after scenario finishes
    public void clear() {
        context.clear();
    }
}