package nathol.jkook.tools.adapter;

import snw.jkook.event.Event;

public interface ListenerInterceptAdapter {

    default boolean preHandle(Event event) {
        return true;
    }

    default void afterHandle(Event event) {}

}
