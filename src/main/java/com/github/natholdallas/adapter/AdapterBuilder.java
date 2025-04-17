package com.github.natholdallas.adapter;

import com.github.natholdallas.annotation.ExpectHandler;
import com.github.natholdallas.handler.ConsoleCommandExecutorHandler;
import com.github.natholdallas.handler.ListenerHandler;
import com.github.natholdallas.utils.Reflection;
import lombok.SneakyThrows;
import snw.jkook.command.CommandExecutor;
import snw.jkook.command.ConsoleCommandExecutor;
import snw.jkook.command.UserCommandExecutor;
import snw.jkook.event.Listener;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public final class AdapterBuilder {

    @SneakyThrows
    public static CommandExecutor addAdapter(CommandExecutor target, List<Class<? extends CommandAdapter>> adapters) {
        AtomicReference<CommandExecutor> executor = new AtomicReference<>(target);
        for (Class<? extends CommandAdapter> it : adapters) {
            ExpectHandler expectHandler = it.getAnnotation(ExpectHandler.class);
            if (expectHandler == null) {
                continue;
            }
            CommandAdapter adapter = Reflection.newInstance(it.getConstructor());
            for (Class<?> value : expectHandler.value()) {
                if (value == target.getClass()) {
                    executor.set((sender, arguments, message) -> {
                        if (adapter.preHandle(sender, arguments, message)) {
                            executor.get().onCommand(sender, arguments, message);
                        }
                        adapter.onComplete(sender, arguments, message);
                    });
                    break;
                }
            }
        }
        return executor.get();
    }

    @SneakyThrows
    public static UserCommandExecutor addAdapter(UserCommandExecutor target, List<Class<? extends UserCommandAdapter>> adapters) {
        AtomicReference<UserCommandExecutor> executor = new AtomicReference<>();
        for (Class<? extends UserCommandAdapter> it : adapters) {
            ExpectHandler expectHandler = it.getAnnotation(ExpectHandler.class);
            if (expectHandler == null) {
                continue;
            }
            UserCommandAdapter adapter = Reflection.newInstance(it.getConstructor());
            for (Class<?> value : expectHandler.value()) {
                if (value == target.getClass()) {
                    executor.set((sender, arguments, message) -> {
                        if (adapter.preHandle(sender, arguments, message)) {
                            executor.get().onCommand(sender, arguments, message);
                        }
                        adapter.onComplete(sender, arguments, message);
                    });
                    break;
                }
            }
        }
        return target;
    }

    @SneakyThrows
    public static ConsoleCommandExecutor addAdapter(ConsoleCommandExecutor target, List<Class<? extends ConsoleCommandAdapter>> adapters) {
        Class<? extends ConsoleCommandExecutor> clazz = target.getClass();
        for (Class<? extends ConsoleCommandAdapter> it : adapters) {
            ConsoleCommandAdapter adapter = Reflection.newInstance(it.getConstructor());
            if (it.isAnnotationPresent(ExpectHandler.class)) {
                ExpectHandler expectHandler = it.getAnnotation(ExpectHandler.class);
                for (Class<?> value : expectHandler.value()) {
                    if (value == clazz) {
                        target = new ConsoleCommandExecutorHandler(target, adapter);
                        break;
                    }
                }
            }
            target = new ConsoleCommandExecutorHandler(target, adapter);
        }
        return target;
    }

    @SneakyThrows
    public static Listener addAdapter(Listener target, List<Class<? extends ListenerAdapter>> adapters) {
        for (Class<? extends ListenerAdapter> it : adapters) {
            ListenerAdapter adapter = Reflection.newInstance(it.getConstructor());
            target = (Listener) Proxy.newProxyInstance(
                    target.getClass().getClassLoader(),
                    target.getClass().getInterfaces(),
                    new ListenerHandler(target, adapter)
            );
        }
        return target;
    }

    private AdapterBuilder() {
    }

}
