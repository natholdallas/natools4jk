package com.github.natholdallas.handler;

import com.github.natholdallas.adapter.ListenerAdapter;
import com.github.natholdallas.annotation.ExpectHandler;
import com.github.natholdallas.utils.Reflection;
import lombok.RequiredArgsConstructor;
import snw.jkook.event.Event;
import snw.jkook.event.Listener;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <blockquote><pre>
 * {@code
 *      class Test {
 *          @EventHandler
 *          @ExceptHandler()
 *          public void a(Event e) {
 *          }
 *      }
 * }
 * </pre></blockquote>
 */
@RequiredArgsConstructor
public class ListenerHandler implements InvocationHandler {

    private final Listener target;
    private final ListenerAdapter adapter;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Object result = null;
        ExpectHandler expectHandler = method.getAnnotation(ExpectHandler.class);
        if (expectHandler != null) {
            for (Class<?> value : expectHandler.value()) {
                if (value != this.adapter.getClass()) {
                    continue;
                }
                Event event = (Event) args[0];
                if (this.adapter.preHandle(event)) {
                    result = Reflection.invoke(method, this.target, args);
                }
                this.adapter.afterHandle(event);
            }
        }
        return result;
    }

}
