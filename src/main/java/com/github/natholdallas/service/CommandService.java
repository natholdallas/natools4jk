package com.github.natholdallas.service;

import com.github.natholdallas.adapter.AdapterBuilder;
import com.github.natholdallas.annotation.NCommand;
import com.github.natholdallas.config.JKConfiguration;
import com.github.natholdallas.utils.Reflection;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import snw.jkook.JKook;
import snw.jkook.command.CommandExecutor;
import snw.jkook.command.ConsoleCommandExecutor;
import snw.jkook.command.JKookCommand;
import snw.jkook.command.UserCommandExecutor;
import snw.jkook.plugin.Plugin;

import java.util.Arrays;

@RequiredArgsConstructor
public final class CommandService implements Service {

    private final Plugin plugin;
    private final JKConfiguration configuration;

    @Override
    public void start() {
        configuration.commands()
                .stream()
                .filter(it -> configuration.commands()
                        .stream()
                        // filter anyone contains in command's subcommand
                        .anyMatch(e -> !Arrays.asList(e.getAnnotation(NCommand.class).subCommands()).contains(it)))
                .forEach(it -> {
                    registCommand(it, it.getAnnotation(NCommand.class).subCommands()).register(plugin);
                    JKook.getLogger().info("Regist a command, the class name is : {}", it.getSimpleName());
                });
    }

    @SneakyThrows
    private JKookCommand registCommand(Class<?> parent, Class<?>[] childs) {
        NCommand command = parent.getAnnotation(NCommand.class);
        JKookCommand jkc = new JKookCommand(command.value());
        if (!command.description().isEmpty()) {
            jkc.setDescription(command.description());
        }
        if (!command.helpContent().isEmpty()) {
            jkc.setHelpContent(command.helpContent());
        }
        for (String prefix : command.prefixes()) {
            jkc.addPrefix(prefix);
        }
        for (String alias : command.aliases()) {
            jkc.addAlias(alias);
        }
        for (Class<?> clazz : command.arguments()) {
            jkc.addArgument(clazz);
        }
        Object executor = Reflection.newInstance(parent.getDeclaredConstructor());
        if (CommandExecutor.class.isAssignableFrom(parent)) {
            jkc.setExecutor(AdapterBuilder.addAdapter((CommandExecutor) executor, configuration.commandAdapters()));
        } else if (UserCommandExecutor.class.isAssignableFrom(parent)) {
            jkc.executesUser(AdapterBuilder.addAdapter((UserCommandExecutor) executor, configuration.userCommandAdapters()));
        } else if (ConsoleCommandExecutor.class.isAssignableFrom(parent)) {
            jkc.executesConsole(AdapterBuilder.addAdapter((ConsoleCommandExecutor) executor, configuration.consoleCommandAdapters()));
        }
        for (Class<?> child : childs) {
            NCommand annotation = child.getAnnotation(NCommand.class);
            if (annotation.subCommands().length > 0) {
                jkc.addSubcommand(registCommand(child, annotation.subCommands()));
            }
        }
        return jkc;
    }

}
