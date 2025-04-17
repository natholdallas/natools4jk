package com.github.natholdallas.utils;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Consumer;

public final class Reflection {

    public static void onCatch(Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void onCatch(Runnable runnable, Consumer<Throwable> consumer) {
        try {
            runnable.run();
        } catch (Throwable e) {
            consumer.accept(e);
        }
    }

    public static <T> T newInstance(@NotNull Constructor<T> constructor, Object... initargs) {
        try {
            constructor.setAccessible(true);
            return constructor.getDeclaringClass().cast(constructor.newInstance(initargs));
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static void set(@NotNull Field field, Object instance, Object value) {
        try {
            field.setAccessible(true);
            field.set(instance, value);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object invoke(@NotNull Method method, Object instance, Object... args) {
        try {
            method.setAccessible(true);
            return method.invoke(instance, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Reflection() {
    }

}
