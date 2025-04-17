package com.github.natholdallas.utils;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import snw.jkook.JKook;

import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;

public final class ClassScanner {

    private ClassScanner() {
    }

    @SneakyThrows
    public static @NotNull Collection<Class<?>> scan(@NotNull Class<?> clazz) {
        Logger logger = JKook.getLogger();
        logger.info("Scanning class...");

        String packagePath = clazz.getPackageName().replace('.', '/');
        List<Class<?>> classes = new ArrayList<>(40);
        Iterator<URL> resources = clazz.getClassLoader()
                .getResources(packagePath)
                .asIterator();
        while (resources.hasNext()) {
            URL url = resources.next();
            switch (url.getProtocol()) {
                case "jar" -> {
                    logger.info("Protocol:jar, open JarURLConnection...");
                    JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                    Iterator<JarEntry> jarEntries = jarURLConnection
                            .getJarFile()
                            .entries()
                            .asIterator();
                    while (jarEntries.hasNext()) {
                        String name = jarEntries.next().getName();
                        if (name.endsWith(".class") && name.startsWith(packagePath)) {
                            String className = name.replace('/', '.').substring(0, name.lastIndexOf(".class"));
                            logger.info("Find class : {}", className);
                            classes.add(Class.forName(className));
                        }
                    }
                }
                case "file" -> {
                    // Cause by jkook framework almost using jar package, so about flushbonading, we wanna working after jar mode
                }
                default -> throw new RuntimeException("InValid Protocol");
            }
        }
        logger.info("Successful to scan your classes");
        return classes;
    }

}
