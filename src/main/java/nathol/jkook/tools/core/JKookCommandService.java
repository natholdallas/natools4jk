package nathol.jkook.tools.core;

import java.lang.reflect.Field;

import org.slf4j.Logger;

import nathol.jkook.tools.annotation.CommandUltimate;
import nathol.jkook.tools.annotation.Ignored;
import nathol.jkook.tools.annotation.RegistJKookCommand;
import nathol.jkook.tools.config.Natools4jkConfiguration;
import nathol.jkook.tools.core.ignore.Reflection;
import nathol.jkook.tools.repository.CommandUltimateRegistry;
import nathol.jkook.tools.config.JKookConfiguration;
import snw.jkook.JKook;
import snw.jkook.command.CommandExecutor;
import snw.jkook.command.ConsoleCommandExecutor;
import snw.jkook.command.JKookCommand;
import snw.jkook.command.UserCommandExecutor;
import snw.jkook.plugin.Plugin;

import static nathol.jkook.tools.core.ignore.ReflectProcessor.*;
import static nathol.jkook.tools.core.ProxyPattern.*;

public final class JKookCommandService {

    private final Plugin plugin;
    private final JKookConfiguration jKookConfiguration;
    private final Natools4jkConfiguration configuration;

    public JKookCommandService(Plugin plugin, JKookConfiguration jKookConfiguration, Natools4jkConfiguration configuration) {
        this.plugin = plugin;
        this.jKookConfiguration = jKookConfiguration;
        this.configuration = configuration;
    }

    public static void start(Plugin plugin, JKookConfiguration jKookConfigurationm, Natools4jkConfiguration configuration) {
        new JKookCommandService(plugin, jKookConfigurationm, configuration);
    }

    public void start() {
        Logger logger = JKook.getLogger();
        this.jKookConfiguration.getCommands()
                .stream()
                .filter(it -> {
                    if (it.isAnnotationPresent(Ignored.class)) {
                        logger.info("The class {} has an {} annotation, ignore it", it.getSimpleName(), Ignored.class.getSimpleName());
                        return false;
                    }
                    for (Class<?> command : this.jKookConfiguration.getCommands()) {
                        RegistJKookCommand annotation = command.getAnnotation(RegistJKookCommand.class);
                        for (Class<?> clazz : annotation.subCommands()) {
                            if (it == clazz && !annotation.forceRegist()) {
                                return false;
                            }
                        }
                    }
                    return true;
                })
                .forEach(it -> {
                    getJKookCommand(it, it.getAnnotation(RegistJKookCommand.class).subCommands()).register(plugin);
                    logger.info("registing a command, class name is : {}", it.getSimpleName());
                });
    }

    private JKookCommand getJKookCommand(Class<?> parent, Class<?>[] childs) {
        RegistJKookCommand registJKookCommand = parent.getAnnotation(RegistJKookCommand.class);
        JKookCommand jKookCommand = new JKookCommand(registJKookCommand.value());
        if (!registJKookCommand.description().isEmpty())
            jKookCommand.setDescription(registJKookCommand.description());
        if (!registJKookCommand.helpContent().isEmpty())
            jKookCommand.setHelpContent(registJKookCommand.helpContent());
        for (String prefix : registJKookCommand.prefixes())
            jKookCommand.addPrefix(prefix);
        for (String alias : registJKookCommand.aliases())
            jKookCommand.addAlias(alias);
        Reflection<?> parentReflect = new Reflection<>(parent);
        Object executor = newInstance(parentReflect.getDeclaredConstructor());
        if (CommandExecutor.class.isAssignableFrom(parent))
            jKookCommand.setExecutor(addCommandExecutorIntercept(
                CommandExecutor.class.cast(executor),
                this.configuration.getCommandInterceptAdapters()
            ));
        if (UserCommandExecutor.class.isAssignableFrom(parent))
            jKookCommand.executesUser(addUserCommandExecutorIntercept(
                UserCommandExecutor.class.cast(executor),
                this.configuration.getUserCommandInterceptAdapters()
            ));
        if (ConsoleCommandExecutor.class.isAssignableFrom(parent))
            jKookCommand.executesConsole(addConsoleCommandExecutorIntercept(
                ConsoleCommandExecutor.class.cast(executor),
                this.configuration.getConsoleCommandInterceptAdapters()
            ));
        parentReflect.getDeclaredMethod(it -> it.isAnnotationPresent(CommandUltimate.class))
                .ifPresent(method -> {
                    CommandUltimateRegistry registry = new CommandUltimateRegistry();
                    invoke(method, executor, registry);
                    Reflection<JKookCommand> reflect = Reflection.of(JKookCommand.class);
                    reflect.preThrow(e -> {
                        throw new RuntimeException("不正确的配置,你的 ReturnType 不为 void, PatameterType 也必须只有 nathol.jkook.tools.annotation.Registry, 详情参考 Natools4jk 文档", e);
                    });
                    Field argument = reflect.getDeclaredField("arguments");
                    Field optionalArgument = reflect.getDeclaredField("optionalArguments");
                    set(argument, jKookCommand, registry.getArguments());
                    set(optionalArgument, jKookCommand, registry.getOptionalArgumentContainer());
                });
        for (Class<?> child : childs) {
            if (child.isAnnotationPresent(Ignored.class)) {
                continue;
            }
            RegistJKookCommand annotation = child.getAnnotation(RegistJKookCommand.class);
            if (annotation.subCommands().length > 0) {
                jKookCommand.addSubcommand(getJKookCommand(child, annotation.subCommands()));
            }
        }
        return jKookCommand;
    }

}
