package nathol.jkook.tools.core.generator;

import java.util.Set;
import java.util.stream.Collectors;

import nathol.jkook.tools.annotation.RegistJKookCommand;
import nathol.jkook.tools.annotation.RegistListener;
import nathol.jkook.tools.config.JKookConfiguration;
import snw.jkook.JKook;
import snw.jkook.command.CommandExecutor;
import snw.jkook.command.ConsoleCommandExecutor;
import snw.jkook.command.UserCommandExecutor;
import snw.jkook.event.Listener;

public final class JKookConfigurationGenerator extends JKookConfiguration {

    public JKookConfigurationGenerator(Set<Class<?>> classes) {
        super(
            generateCommandExecutors(classes),
            generateListeners(classes)
        );
    }

    public static Set<Class<?>> generateCommandExecutors(Set<Class<?>> classes) {
        return classes
                .stream()
                .filter(it -> {
                    Class<CommandExecutor> normal = CommandExecutor.class;
                    Class<UserCommandExecutor> user = UserCommandExecutor.class;
                    Class<ConsoleCommandExecutor> console = ConsoleCommandExecutor.class;
                    boolean isCommandExecutor = normal.isAssignableFrom(it);
                    boolean isUserCommandExecutor = user.isAssignableFrom(it);
                    boolean isConsoleCommandExecutor = console.isAssignableFrom(it);
                    boolean filter = it.isAnnotationPresent(RegistJKookCommand.class)
                            && isCommandExecutor
                            || isUserCommandExecutor
                            || isConsoleCommandExecutor;
                    if (filter) {
                        String name = null;
                        if (isCommandExecutor) {
                            name = normal.getSimpleName();
                        } else if (isUserCommandExecutor) {
                            name = user.getSimpleName();
                        } else if (isConsoleCommandExecutor) {
                            name = console.getSimpleName();
                        }
                        JKook.getLogger().info("Find a {}, the class name is {}", name, it.getSimpleName());
                    }
                    return filter;
                })
                .collect(Collectors.toSet());
    }

    public static Set<Class<? extends Listener>> generateListeners(Set<Class<?>> classes) {
        return classes
                .stream()
                .filter(it -> it.isAnnotationPresent(RegistListener.class)
                        && Listener.class.isAssignableFrom(it))
                .map(it -> {
                    Class<? extends Listener> listener = it.asSubclass(Listener.class);
                    JKook.getLogger().info("Find a {}, the class name is {}", Listener.class.getSimpleName(), it.getSimpleName());
                    return listener;
                })
                .collect(Collectors.toSet());
    }

}
