package com.github.natholdallas.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import snw.jkook.JKook;

import java.util.HashMap;
import java.util.Map;

public final class Transfer {

    public static final Map<Object, Object> map = new HashMap<>();

    private Transfer() {
    }

    public static void put(@NotNull Object key, @NotNull Object value) {
        map.put(key, value);
    }

    public static <T> @Nullable T get(@NotNull Object key, @NotNull Class<T> clazz) {
        Object object = map.get(key);
        if (object == null) {
            return null;
        }
        T instance = clazz.cast(object);
        map.remove(key);
        return instance;
    }

    public static void ls() {
        map.forEach((k, v) -> JKook.getLogger().info("[{} : {}]", k, v));
    }

}
