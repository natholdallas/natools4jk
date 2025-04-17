package com.github.natholdallas;

import com.github.natholdallas.config.JKConfiguration;
import com.github.natholdallas.service.CommandService;
import com.github.natholdallas.service.ListenerService;
import com.github.natholdallas.service.Service;
import org.jetbrains.annotations.NotNull;
import snw.jkook.JKook;
import snw.jkook.plugin.Plugin;

public final class NatoolsApplication {

    private static long start;

    static {
        JKook.getLogger().info("NatoolsApplication-starting...");
    }

    private NatoolsApplication() {
    }

    public static <T extends Plugin> void start(@NotNull T plugin) {
        JKook.getLogger().info("Using automatic mode, starting auto configuration...");
        start = System.currentTimeMillis();
        start(plugin, JKConfiguration.autoConfiguration(plugin.getClass()));
    }

    public static <T extends Plugin> void start(@NotNull T plugin, @NotNull JKConfiguration configuration) {
        start = start == 0 ? System.currentTimeMillis() : start;
        Service.starter(
                new CommandService(plugin, configuration),
                new ListenerService(plugin, configuration)
        );
        JKook.getLogger().info("""
                         _   _    _______          _                          _ _           _   _
                        | \\ | |  |__   __|        | |       /\\               | (_)         | | (_)
                        |  \\| | __ _| | ___   ___ | |___   /  \\   _ __  _ __ | |_  ___ __ _| |_ _  ___  _ __
                        | . ` |/ _` | |/ _ \\ / _ \\| / __| / /\\ \\ | '_ \\| '_ \\| | |/ __/ _` | __| |/ _ \\| '_ \\
                        | |\\  | (_| | | (_) | (_) | \\__ \\/ ____ \\| |_) | |_) | | | (_| (_| | |_| | (_) | | | |
                        |_| \\_|\\__,_|_|\\___/ \\___/|_|___/_/    \\_\\ .__/| .__/|_|_|\\___\\__,_|\\__|_|\\___/|_| |_|
                                                                 | |   | |
                                                                 |_|   |_|
                        服务耗时: {}ms
                """, System.currentTimeMillis() - start);
        System.gc();
    }

}
