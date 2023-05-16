package nathol.jkook.tools;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import nathol.jkook.tools.config.Natools4jkConfiguration;
import nathol.jkook.tools.config.JKookConfiguration;
import nathol.jkook.tools.core.JKookCommandService;
import nathol.jkook.tools.core.ListenerService;
import nathol.jkook.tools.core.generator.JKookConfigurationGenerator;
import nathol.jkook.tools.core.generator.Natools4jkConfigurationGenerator;
import snw.jkook.JKook;
import snw.jkook.plugin.Plugin;

import static nathol.jkook.tools.ClassScanner.classesScan;

import java.util.Objects;
import java.util.Set;

public final class NatoolsApplication {

    public static long start = 0;

    private NatoolsApplication() {}

    public static <T extends Plugin> void start(@NotNull T plugin) {
        start = System.currentTimeMillis();
        Logger logger = JKook.getLogger();
        logger.info("Using Fully-automatic mode, auto config all components and configurations");
        logger.info("Generating Configuration");
        Set<Class<?>> classes = classesScan(plugin.getClass());
        start(
            plugin,
            new JKookConfigurationGenerator(classes),
            new Natools4jkConfigurationGenerator(classes)
        );
    }

    public static <T extends Plugin> void start(@NotNull T plugin, @NotNull JKookConfiguration jkook, @NotNull Natools4jkConfiguration configuration) {
        Objects.requireNonNull(plugin);
        Objects.requireNonNull(jkook);
        Objects.requireNonNull(configuration);
        start = start == 0 ? System.currentTimeMillis() : start;
        Logger logger = JKook.getLogger();
        logger.info("NatoolsApplication-starting...");
        JKookCommandService.start(plugin, jkook, configuration);
        ListenerService.start(plugin, jkook, configuration);
        logger.info(
            """

             _   _    _______          _                          _ _           _   _
            | \\ | |  |__   __|        | |       /\\               | (_)         | | (_)
            |  \\| | __ _| | ___   ___ | |___   /  \\   _ __  _ __ | |_  ___ __ _| |_ _  ___  _ __
            | . ` |/ _` | |/ _ \\ / _ \\| / __| / /\\ \\ | '_ \\| '_ \\| | |/ __/ _` | __| |/ _ \\| '_ \\
            | |\\  | (_| | | (_) | (_) | \\__ \\/ ____ \\| |_) | |_) | | | (_| (_| | |_| | (_) | | | |
            |_| \\_|\\__,_|_|\\___/ \\___/|_|___/_/    \\_\\ .__/| .__/|_|_|\\___\\__,_|\\__|_|\\___/|_| |_|
                                                     | |   | |
                                                     |_|   |_|
            Thanks for SNWCreations ! 2023-04-28
            """
        );
        logger.info("服务耗时: " + (System.currentTimeMillis() - start) + "ms");
        System.gc();
    }

}
