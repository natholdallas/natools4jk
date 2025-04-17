package com.github.natholdallas.config;

import com.github.natholdallas.adapter.CommandAdapter;
import com.github.natholdallas.adapter.ConsoleCommandAdapter;
import com.github.natholdallas.adapter.ListenerAdapter;
import com.github.natholdallas.adapter.UserCommandAdapter;
import org.jetbrains.annotations.NotNull;
import snw.jkook.JKook;
import snw.jkook.command.CommandExecutor;
import snw.jkook.event.Listener;
import snw.jkook.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

import static com.github.natholdallas.utils.ClassScanner.scan;

public record JKConfiguration(

        /* Command */
        List<Class<?>> commands,
        List<Class<? extends CommandAdapter>> commandAdapters,
        List<Class<? extends UserCommandAdapter>> userCommandAdapters,
        List<Class<? extends ConsoleCommandAdapter>> consoleCommandAdapters,

        /* Listener */
        List<Class<? extends Listener>> listeners,
        List<Class<? extends ListenerAdapter>> listenerAdapters

) {

    public static JKConfiguration autoConfiguration(@NotNull Class<? extends Plugin> rootClass) {
        final List<Class<?>> commands = new ArrayList<>();
        final List<Class<? extends CommandAdapter>> commandAdapters = new ArrayList<>();
        final List<Class<? extends UserCommandAdapter>> userCommandAdapters = new ArrayList<>();
        final List<Class<? extends ConsoleCommandAdapter>> consoleCommandAdapters = new ArrayList<>();
        final List<Class<? extends Listener>> listeners = new ArrayList<>();
        final List<Class<? extends ListenerAdapter>> listenerAdapters = new ArrayList<>();
        scan(rootClass).forEach(it -> {
            Class<?> clazz;
            if (CommandExecutor.class.isAssignableFrom(it)) {
                commands.add(it);
                clazz = CommandExecutor.class;
            } else if (CommandAdapter.class.isAssignableFrom(it)) {
                commandAdapters.add(it.asSubclass(CommandAdapter.class));
                clazz = CommandAdapter.class;
            } else if (UserCommandAdapter.class.isAssignableFrom(it)) {
                userCommandAdapters.add(it.asSubclass(UserCommandAdapter.class));
                clazz = UserCommandAdapter.class;
            } else if (ConsoleCommandAdapter.class.isAssignableFrom(it)) {
                consoleCommandAdapters.add(it.asSubclass(ConsoleCommandAdapter.class));
                clazz = ConsoleCommandAdapter.class;
            } else if (Listener.class.isAssignableFrom(it)) {
                listeners.add(it.asSubclass(Listener.class));
                clazz = Listener.class;
            } else if (ListenerAdapter.class.isAssignableFrom(it)) {
                listenerAdapters.add(it.asSubclass(ListenerAdapter.class));
                clazz = ListenerAdapter.class;
            } else {
                return;
            }
            JKook.getLogger().info("Find a {}, the class name is {}", clazz.getSimpleName(), it.getSimpleName());
        });
        return new JKConfiguration(commands, commandAdapters, userCommandAdapters, consoleCommandAdapters, listeners, listenerAdapters);
    }

}
