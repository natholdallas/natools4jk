package com.github.natholdallas.service;

import org.jetbrains.annotations.NotNull;
import snw.jkook.JKook;

public interface Service {

    void start();

    static void starter(Service @NotNull ... services) {
        for (Service service : services) {
            String name = service.getClass().getSimpleName();
            JKook.getLogger().info("{} : starting...", name);
            service.start();
            JKook.getLogger().info("{} : started!", name);
        }
    }

}
