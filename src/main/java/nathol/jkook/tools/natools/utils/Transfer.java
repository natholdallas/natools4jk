package nathol.jkook.tools.natools.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import snw.jkook.JKook;

public class Transfer {
    public static final Map<String, Object> map = new HashMap<>();

    public static void mount(String key, Object value){
        map.put(key, value);
    }

    public static <T> T extract(String value, Class<T> typeOfClass){
        Object object = map.get(value);
        if(object == null) return null;
        return typeOfClass.cast(object);
    }

    public static void close(String value){
        if(map.get(value) == null) return;
        map.remove(value);
    }

    public static void clear(){
        map.clear();
    }

    public static void ls(){
        Logger logger = JKook.getLogger();
        map.entrySet().forEach(l -> {
            logger.info(
                """
                    [ id: {} ] [ value : {} ]
                    -------------------
                    """,
                l.getKey(),
                l.getValue()
            );
        });
    }
}
