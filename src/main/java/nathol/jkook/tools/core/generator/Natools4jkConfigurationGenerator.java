package nathol.jkook.tools.core.generator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import nathol.jkook.tools.adapter.CommandExecutorInterceptAdapter;
import nathol.jkook.tools.adapter.ConsoleCommandExecutorInterceptAdapter;
import nathol.jkook.tools.adapter.ListenerInterceptAdapter;
import nathol.jkook.tools.adapter.UserCommandExecutorInterceptAdapter;
import nathol.jkook.tools.config.Natools4jkConfiguration;
import snw.jkook.JKook;

public class Natools4jkConfigurationGenerator extends Natools4jkConfiguration {

    public Natools4jkConfigurationGenerator(Set<Class<?>> classes) {
        super(
            generateCommandInterceptAdapters(classes),
            generateUserCommandInterceptAdapters(classes),
            generateConsoleCommandInterceptAdapters(classes),
            generateListenerInterceptAdapters(classes)
        );
    }

    public static List<Class<? extends CommandExecutorInterceptAdapter>> generateCommandInterceptAdapters(Set<Class<?>> classes) {
        return classes
                .stream()
                .filter(CommandExecutorInterceptAdapter.class::isAssignableFrom)
                .map(it -> {
                    Class<? extends CommandExecutorInterceptAdapter> clazz = it.asSubclass(CommandExecutorInterceptAdapter.class);
                    JKook.getLogger().info("Find a {}, the class name is {}", CommandExecutorInterceptAdapter.class.getSimpleName(), it.getSimpleName());
                    return clazz;
                })
                .collect(Collectors.toList());
    }

    public static List<Class<? extends UserCommandExecutorInterceptAdapter>> generateUserCommandInterceptAdapters(Set<Class<?>> classes) {
        return classes
                .stream()
                .filter(UserCommandExecutorInterceptAdapter.class::isAssignableFrom)
                .map(it -> {
                    Class<? extends UserCommandExecutorInterceptAdapter> clazz = it.asSubclass(UserCommandExecutorInterceptAdapter.class);
                    JKook.getLogger().info("Find a {}, the class name is {}", UserCommandExecutorInterceptAdapter.class.getSimpleName(), it.getSimpleName());
                    return clazz;
                })
                .collect(Collectors.toList());
    }

    public static List<Class<? extends ConsoleCommandExecutorInterceptAdapter>> generateConsoleCommandInterceptAdapters(Set<Class<?>> classes) {
        return classes
                .stream()
                .filter(ConsoleCommandExecutorInterceptAdapter.class::isAssignableFrom)
                .map(it -> {
                    Class<? extends ConsoleCommandExecutorInterceptAdapter> clazz = it.asSubclass(ConsoleCommandExecutorInterceptAdapter.class);
                    JKook.getLogger().info("Find a {}, the class name is {}", ConsoleCommandExecutorInterceptAdapter.class.getSimpleName(), it.getSimpleName());
                    return clazz;
                })
                .collect(Collectors.toList());
    }

    public static List<Class<? extends ListenerInterceptAdapter>> generateListenerInterceptAdapters(Set<Class<?>> classes) {
        return classes
                .stream()
                .filter(ListenerInterceptAdapter.class::isAssignableFrom)
                .map(it -> {
                    Class<? extends ListenerInterceptAdapter> clazz = it.asSubclass(ListenerInterceptAdapter.class);
                    JKook.getLogger().info("Find a {}, the class name is {}", ListenerInterceptAdapter.class.getSimpleName(), it.getSimpleName());
                    return clazz;
                })
                .collect(Collectors.toList());
    }

}
