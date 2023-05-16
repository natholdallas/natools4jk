package nathol.jkook.tools.core.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import nathol.jkook.tools.adapter.ListenerInterceptAdapter;
import nathol.jkook.tools.annotation.Routes;
import nathol.jkook.tools.core.ignore.ReflectProcessor;
import snw.jkook.event.Event;
import snw.jkook.event.Listener;

public class ListenerHandler implements InvocationHandler {

    private final Listener target;
    private final ListenerInterceptAdapter adapter;

    public ListenerHandler(Listener listener, ListenerInterceptAdapter adapter) {
        this.target = listener;
        this.adapter = adapter;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        AtomicReference<Object> atomic = new AtomicReference<>(result);
        Optional.ofNullable(method.getAnnotation(Routes.class))
                .ifPresent(routes -> {
                    for (Class<?> value : routes.value()) {
                        if (value != this.adapter.getClass()) {
                            continue;
                        }
                        Event event = (Event) args[0];
                        if (this.adapter.preHandle(event)) {
                            atomic.set(ReflectProcessor.invoke(method, this.target, args));
                        }
                        this.adapter.afterHandle(event);
                    }
                });
        return atomic.get();
    }

}
