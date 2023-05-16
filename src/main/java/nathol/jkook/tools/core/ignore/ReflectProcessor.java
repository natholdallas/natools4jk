package nathol.jkook.tools.core.ignore;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class ReflectProcessor {

    public static Object invoke(Method method, Object instance, Object... args) {
        try {
            method.setAccessible(true);
            return method.invoke(instance, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T newInstance(Constructor<T> constructor, Object... initargs) {
        try {
            constructor.setAccessible(true);
            return constructor.newInstance(initargs);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static void set(Field field, Object instance, Object value) {
        try {
            field.setAccessible(true);
            field.set(instance, value);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
