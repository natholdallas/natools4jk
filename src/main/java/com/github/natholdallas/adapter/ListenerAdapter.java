package com.github.natholdallas.adapter;

import snw.jkook.event.Event;

public interface ListenerAdapter {

    default boolean preHandle(Event event) {
        return true;
    }

    default void afterHandle(Event event) {}

}
