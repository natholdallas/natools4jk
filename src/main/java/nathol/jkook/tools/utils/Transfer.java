package nathol.jkook.tools.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import snw.jkook.JKook;

public final class Transfer {

    public static final Map<String, Object> map = new HashMap<>();

    private Transfer() {
    }

    public static void mount(String key, Object value) {
        map.put(key, value);
    }

    public static <T> T extract(String value, Class<T> typeOfClass) {
        Object object = map.get(value);
        if (object == null)
            return null;
        T instance = typeOfClass.cast(object);
        map.remove(value);
        return instance;
    }

    public static void ls() {
        Logger logger = JKook.getLogger();
        map.entrySet().forEach(l -> {
            logger.info("id: {}", l.getKey());
            logger.info("value: {}", l.getValue());
        });
    }

}
