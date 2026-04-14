package com.veeva.context;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScenarioContext {
    private static final Logger log = LogManager.getLogger(ScenarioContext.class);
    private final Map<String, Object> context = new HashMap<>();

    public void set(String key, Object value) {
        log.debug("💾 CONTEXT SET: [{}] = {}", key, value);
        context.put(key, value);
    }

    public Object get(String key) {
        Object value = context.get(key);
        if (value == null) {
            log.error("❌ CONTEXT ERROR: Key [{}] not found! Current keys: {}", key, context.keySet());
        }
        return value;
    }

    public long getLong(String key) {
        Object val = get(key);
        if (val == null) return 0L;
        return ((Number) val).longValue();
    }

    public String getString(String key) {
        Object val = get(key);
        return (val == null) ? "" : String.valueOf(val);
    }

    public int getInt(String key) {
        Object val = get(key);
        if (val == null) return 0;
        return ((Number) val).intValue();
    }

    public void clear() {
        log.debug("🧹 CONTEXT CLEARED");
        context.clear();
    }
}