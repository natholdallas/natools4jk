package nathol.jkook.tools;

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
        T instance = typeOfClass.cast(object);
        map.remove(value);
        return instance;
    }

    public static void ls(){
        Logger logger = JKook.getLogger();
        map.entrySet().forEach(l -> {
            logger.info(
                """
                    [ id: {} ] [ value : {} ]
                    -------------------------
                    """,
                l.getKey(),
                l.getValue()
            );
        });
    }
}
