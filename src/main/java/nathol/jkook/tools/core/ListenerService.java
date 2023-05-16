package nathol.jkook.tools.core;

import org.slf4j.Logger;

import nathol.jkook.tools.config.Natools4jkConfiguration;
import nathol.jkook.tools.core.ignore.Reflection;
import nathol.jkook.tools.annotation.Ignored;
import nathol.jkook.tools.config.JKookConfiguration;
import snw.jkook.JKook;
import snw.jkook.event.Listener;
import snw.jkook.plugin.Plugin;

import static nathol.jkook.tools.core.ignore.ReflectProcessor.*;
import static nathol.jkook.tools.core.ProxyPattern.*;

public final class ListenerService {

    private final Plugin plugin;
    private final JKookConfiguration jKookConfiguration;
    private final Natools4jkConfiguration configuration;

    private ListenerService(Plugin plugin, JKookConfiguration jKookConfiguration, Natools4jkConfiguration configuration) {
        this.plugin = plugin;
        this.jKookConfiguration = jKookConfiguration;
        this.configuration = configuration;
    }

    public static void start(Plugin plugin, JKookConfiguration jKookConfiguration, Natools4jkConfiguration configuration) {
        new ListenerService(plugin, jKookConfiguration, configuration).start();
    }

    public void start() {
        this.jKookConfiguration
                .getListeners()
                .forEach(it -> {
                    Logger logger = JKook.getLogger();
                    if (it.isAnnotationPresent(Ignored.class)) {
                        logger.info("The class {} has an {} annotation, ignore it", it.getSimpleName(), Ignored.class.getSimpleName());
                        return;
                    }
                    Reflection<? extends Listener> reflect = new Reflection<>(it);
                    Listener listener = newInstance(reflect.getConstructor());
                    listener = addListenerIntercept(listener, this.configuration.getListenerInterceptAdapters());
                    JKook.getEventManager().registerHandlers(this.plugin, listener);
                    logger.info("registing a {}, the class name is : {}", Listener.class.getSimpleName(), it.getSimpleName());
                });
    }

}
