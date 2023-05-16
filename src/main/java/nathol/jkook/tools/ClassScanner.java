package nathol.jkook.tools;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.jar.JarEntry;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import snw.jkook.JKook;

public final class ClassScanner {

    private ClassScanner() {}

    static Set<Class<?>> classesScan(@NotNull Class<?> clazz) {
        Objects.requireNonNull(clazz);
        String packagePath = clazz.getPackageName().replace('.', '/');
        Logger logger = JKook.getLogger();
        logger.info("Scanning class");
        Set<Class<?>> classes = new HashSet<>();
        try {
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
                                logger.info("Find class : " + className);
                                classes.add(Class.forName(className));
                            }
                        }
                    }
                    case "file" -> {
                        // Cause by jkook framework almost using jar package, so about flushbonading, we wanna working after jar mode
                    }
                    default -> {
                        throw new RuntimeException("InValid Protocol");
                    }
                }
            }
            logger.info("Successful to scan your classes");
        } catch (IOException | ClassNotFoundException exception) {
            throw new RuntimeException(exception);
        }
        return classes;
    }

}
