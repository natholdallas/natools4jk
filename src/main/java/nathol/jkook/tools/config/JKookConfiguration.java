package nathol.jkook.tools.config;

import java.util.Objects;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

import nathol.jkook.tools.annotation.RegistJKookCommand;
import snw.jkook.command.CommandExecutor;
import snw.jkook.command.ConsoleCommandExecutor;
import snw.jkook.command.UserCommandExecutor;
import snw.jkook.event.Listener;

public class JKookConfiguration {

    private final Set<Class<?>> commands;
    private final Set<Class<? extends Listener>> listeners;

    public JKookConfiguration(@NotNull Set<Class<?>> commands, @NotNull Set<Class<? extends Listener>> listeners) {
        this.commands = Objects.requireNonNull(commands);
        this.listeners = Objects.requireNonNull(listeners);
        commands.forEach(it -> {
            isCommand(it, it.getSimpleName() + " not a command class");
            for (Class<?> subCommmand : it.getAnnotation(RegistJKookCommand.class).subCommands()) {
                isCommand(subCommmand, "The attribute of your" + subCommmand.getSimpleName() + "'s annotation is not valid");
            }
        });
    }

    public Set<Class<?>> getCommands() {
        return commands;
    }

    public Set<Class<? extends Listener>> getListeners() {
        return listeners;
    }

    private static void isCommand(@NotNull Class<?> clazz, @NotNull String message) {
        if (
            CommandExecutor.class.isAssignableFrom(clazz)
            || UserCommandExecutor.class.isAssignableFrom(clazz)
            || ConsoleCommandExecutor.class.isAssignableFrom(clazz)
        ) return;
        throw new RuntimeException(message);
    }

}
