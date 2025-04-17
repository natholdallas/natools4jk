package com.github.natholdallas.service;

import com.github.natholdallas.adapter.AdapterBuilder;
import com.github.natholdallas.config.JKConfiguration;
import com.github.natholdallas.utils.Reflection;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import snw.jkook.JKook;
import snw.jkook.event.Listener;
import snw.jkook.plugin.Plugin;

@RequiredArgsConstructor
public final class ListenerService implements Service {

    private final Plugin plugin;
    private final JKConfiguration configuration;

    @SneakyThrows
    @Override
    public void start() {
        for (Class<? extends Listener> it : configuration.listeners()) {
            Listener listener = Reflection.newInstance(it.getConstructor());
            listener = AdapterBuilder.addAdapter(listener, configuration.listenerAdapters());
            JKook.getEventManager().registerHandlers(plugin, listener);
            JKook.getLogger().info("registing a {}, the class name is : {}", Listener.class.getSimpleName(), it.getSimpleName());
        }
    }

}
