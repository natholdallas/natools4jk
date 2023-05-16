package nathol.jkook.tools.core;

import java.lang.reflect.Proxy;
import java.util.List;

import nathol.jkook.tools.adapter.CommandExecutorInterceptAdapter;
import nathol.jkook.tools.adapter.ConsoleCommandExecutorInterceptAdapter;
import nathol.jkook.tools.adapter.ListenerInterceptAdapter;
import nathol.jkook.tools.adapter.UserCommandExecutorInterceptAdapter;
import nathol.jkook.tools.annotation.Ignored;
import nathol.jkook.tools.annotation.Routes;
import nathol.jkook.tools.core.handler.CommandExecutorHandler;
import nathol.jkook.tools.core.handler.ConsoleCommandExecutorHandler;
import nathol.jkook.tools.core.handler.ListenerHandler;
import nathol.jkook.tools.core.handler.UserCommandExecutorHandler;
import nathol.jkook.tools.core.ignore.Reflection;
import snw.jkook.JKook;
import snw.jkook.command.CommandExecutor;
import snw.jkook.command.ConsoleCommandExecutor;
import snw.jkook.command.UserCommandExecutor;
import snw.jkook.event.Listener;

import static nathol.jkook.tools.core.ignore.ReflectProcessor.*;

public class ProxyPattern {

    static CommandExecutor addCommandExecutorIntercept(CommandExecutor target, List<Class<? extends CommandExecutorInterceptAdapter>> adapters) {
        Class<? extends CommandExecutor> clazz = target.getClass();
        for (Class<? extends CommandExecutorInterceptAdapter> it : adapters) {
            if (it.isAnnotationPresent(Ignored.class)) {
                JKook.getLogger().info("The class {} has an {} annotation, ignore it", it.getSimpleName(), Ignored.class.getSimpleName());
                continue;
            }
            Reflection<? extends CommandExecutorInterceptAdapter> reflect = new Reflection<>(it);
            CommandExecutorInterceptAdapter adapter = newInstance(reflect.getDeclaredConstructor());
            if (it.isAnnotationPresent(Routes.class)) {
                Routes routes = it.getAnnotation(Routes.class);
                for (Class<?> value : routes.value()) {
                    if (value == clazz) {
                        target = new CommandExecutorHandler(target, adapter);
                        break;
                    }
                }
            }
            target = new CommandExecutorHandler(target, adapter);
        }
        return target;
    }

    static UserCommandExecutor addUserCommandExecutorIntercept(UserCommandExecutor target, List<Class<? extends UserCommandExecutorInterceptAdapter>> adapters) {
        Class<? extends UserCommandExecutor> clazz = target.getClass();
        for (Class<? extends UserCommandExecutorInterceptAdapter> it : adapters) {
            if (it.isAnnotationPresent(Ignored.class)) {
                JKook.getLogger().info("The class {} has an {} annotation, ignore it", it.getSimpleName(), Ignored.class.getSimpleName());
                continue;
            }
            Reflection<? extends UserCommandExecutorInterceptAdapter> reflect = new Reflection<>(it);
            UserCommandExecutorInterceptAdapter adapter = newInstance(reflect.getDeclaredConstructor());
            if (it.isAnnotationPresent(Routes.class)) {
                Routes routes = it.getAnnotation(Routes.class);
                for (Class<?> value : routes.value()) {
                    if (value == clazz) {
                        target = new UserCommandExecutorHandler(target, adapter);
                        break;
                    }
                }
            }
            target = new UserCommandExecutorHandler(target, adapter);
        }
        return target;
    }

    static ConsoleCommandExecutor addConsoleCommandExecutorIntercept(ConsoleCommandExecutor target, List<Class<? extends ConsoleCommandExecutorInterceptAdapter>> adapters) {
        Class<? extends ConsoleCommandExecutor> clazz = target.getClass();
        for (Class<? extends ConsoleCommandExecutorInterceptAdapter> it : adapters) {
            if (it.isAnnotationPresent(Ignored.class)) {
                JKook.getLogger().info("The class {} has an {} annotation, ignore it", it.getSimpleName(), Ignored.class.getSimpleName());
                continue;
            }
            Reflection<? extends ConsoleCommandExecutorInterceptAdapter> reflect = new Reflection<>(it);
            ConsoleCommandExecutorInterceptAdapter adapter = newInstance(reflect.getDeclaredConstructor());
            if (it.isAnnotationPresent(Routes.class)) {
                Routes routes = it.getAnnotation(Routes.class);
                for (Class<?> value : routes.value()) {
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

    static Listener addListenerIntercept(Listener target, List<Class<? extends ListenerInterceptAdapter>> adapters) {
        for (Class<? extends ListenerInterceptAdapter> it : adapters) {
            if (it.isAnnotationPresent(Ignored.class)) {
                JKook.getLogger().info("The class {} has an {} annotation, ignore it", it.getSimpleName(), Ignored.class.getSimpleName());
                continue;
            }
            Reflection<? extends ListenerInterceptAdapter> reflect = new Reflection<>(it);
            ListenerInterceptAdapter adapter = newInstance(reflect.getDeclaredConstructor());
            target = (Listener) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new ListenerHandler(target, adapter)
            );
        }
        return target;
    }

}
