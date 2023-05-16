package nathol.jkook.tools.core.ignore;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;

public final class Reflection<T> {

    private final Class<T> clazz;
    private Consumer<Throwable> consumer;

    public Reflection(@NotNull Class<T> clazz) {
        this(clazz,  e -> { throw new RuntimeException(e);});
    }

    public Reflection(@NotNull Class<T> clazz, @NotNull Consumer<Throwable> consumer) {
        this.clazz = Objects.requireNonNull(clazz);
        this.consumer = Objects.requireNonNull(consumer);
    }

    public static <T> Reflection<T> of(@NotNull Class<T> clazz) {
        return new Reflection<>(clazz);
    }

    public static <T> Reflection<T> of(@NotNull Class<T> clazz, @NotNull Consumer<Throwable> consumer) {
        return new Reflection<>(clazz, consumer);
    }

    public Reflection<T> preThrow(@NotNull Consumer<Throwable> thrower) {
        this.consumer = Objects.requireNonNull(thrower);
        return this;
    }

    public Constructor<T> getDeclaredConstructor(Class<?>... parameterTypes) {
        try {
            return this.clazz.getDeclaredConstructor(parameterTypes);
        } catch (NoSuchMethodException | SecurityException e) {
            this.consumer.accept(e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public Optional<Constructor<T>> getDeclaredConstructor(Predicate<Constructor<T>> predicate) {
        try {
            return Stream.of(this.clazz.getDeclaredConstructors())
                    .map(it -> (Constructor<T>) it)
                    .filter(predicate)
                    .findAny();
        } catch (SecurityException e) {
            this.consumer.accept(e);
        }
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    public List<Constructor<T>> getDeclaredConstructors() {
        try {
            return Stream.of(this.clazz.getDeclaredConstructors())
                    .map(it -> (Constructor<T>) it)
                    .toList();
        } catch (SecurityException e) {
            this.consumer.accept(e);
        }
        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    public List<Constructor<T>> getDeclaredConstructors(Predicate<Constructor<T>> predicate) {
        try {
            return Stream.of(this.clazz.getDeclaredConstructors())
                    .map(it -> (Constructor<T>) it)
                    .filter(predicate)
                    .toList();
        } catch (SecurityException e) {
            this.consumer.accept(e);
        }
        return Collections.emptyList();
    }

    public Method getDeclaredMethod(String name, Class<?>... parameterTypes) {
        try {
            return this.clazz.getDeclaredMethod(name, parameterTypes);
        } catch (NoSuchMethodException | SecurityException e) {
            this.consumer.accept(e);
        }
        return null;
    }

    public Optional<Method> getDeclaredMethod(Predicate<Method> predicate) {
        try {
            return Stream.of(this.clazz.getMethods())
                    .filter(predicate)
                    .findAny();
        } catch (SecurityException e) {
            this.consumer.accept(e);
        }
        return Optional.empty();
    }

    public List<Method> getDeclaredMethods() {
        try {
            return Stream.of(this.clazz.getDeclaredMethods())
                    .toList();
        } catch (SecurityException e) {
            this.consumer.accept(e);
        }
        return Collections.emptyList();
    }

    public List<Method> getDeclaredMethods(Predicate<Method> predicate) {
        try {
            return Stream.of(this.clazz.getDeclaredMethods())
                    .filter(predicate)
                    .toList();
        } catch (SecurityException e) {
            this.consumer.accept(e);
        }
        return Collections.emptyList();
    }

    public Field getDeclaredField(String name) {
        try {
            return this.clazz.getDeclaredField(name);
        } catch (NoSuchFieldException | SecurityException e) {
            this.consumer.accept(e);
        }
        return null;
    }

    public Optional<Field> getDeclaredField(Predicate<Field> predicate) {
        try {
            return Stream.of(this.clazz.getDeclaredFields())
                    .filter(predicate)
                    .findAny();
        } catch (SecurityException e) {
            this.consumer.accept(e);
        }
        return Optional.empty();
    }

    public List<Field> getDeclaredFields() {
        try {
            return Stream.of(this.clazz.getDeclaredFields())
                    .toList();
        } catch (SecurityException e) {
            this.consumer.accept(e);
        }
        return Collections.emptyList();
    }

    public List<Field> getDeclaredFields(Predicate<Field> predicate) {
        try {
            return Stream.of(this.clazz.getDeclaredFields())
                    .filter(predicate)
                    .toList();
        } catch (SecurityException e) {
            this.consumer.accept(e);
        }
        return Collections.emptyList();
    }

    public Constructor<T> getConstructor(Class<?>... parameterTypes) {
        try {
            return this.clazz.getConstructor(parameterTypes);
        } catch (NoSuchMethodException | SecurityException e) {
            this.consumer.accept(e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public Optional<Constructor<T>> getConstructor(Predicate<Constructor<T>> predicate) {
        try {
            return Stream.of(this.clazz.getConstructors())
                    .map(it -> (Constructor<T>) it)
                    .filter(predicate)
                    .findAny();
        } catch (SecurityException e) {
            this.consumer.accept(e);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<Constructor<T>> getConstructors() {
        try {
            return Stream.of(this.clazz.getConstructors())
                    .map(it -> (Constructor<T>) it)
                    .toList();
        } catch (SecurityException e) {
            this.consumer.accept(e);
        }
        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    public List<Constructor<T>> getConstructors(Predicate<Constructor<T>> predicate) {
        try {
            return Stream.of(this.clazz.getConstructors())
                    .map(it -> (Constructor<T>) it)
                    .filter(predicate)
                    .toList();
        } catch (SecurityException e) {
            this.consumer.accept(e);
        }
        return Collections.emptyList();
    }

    public Method getMethod(String name, Class<?>... parameterTypes) {
        try {
            return this.clazz.getMethod(name, parameterTypes);
        } catch (NoSuchMethodException | SecurityException e) {
            this.consumer.accept(e);
        }
        return null;
    }

    public Optional<Method> getMethod(Predicate<Method> predicate) {
        try {
            return Stream.of(this.clazz.getMethods())
                    .filter(predicate)
                    .findAny();
        } catch (SecurityException e) {
            this.consumer.accept(e);
        }
        return Optional.empty();
    }

    public List<Method> getMethods() {
        try {
            return Stream.of(this.clazz.getMethods())
                    .toList();
        } catch (SecurityException e) {
            this.consumer.accept(e);
        }
        return Collections.emptyList();
    }

    public List<Method> getMethods(Predicate<Method> predicate) {
        try {
            return Stream.of(this.clazz.getMethods())
                    .filter(predicate)
                    .toList();
        } catch (SecurityException e) {
            this.consumer.accept(e);
        }
        return Collections.emptyList();
    }

    public Field getField(String name) {
        try {
            return this.clazz.getField(name);
        } catch (NoSuchFieldException | SecurityException e) {
            this.consumer.accept(e);
        }
        return null;
    }

    public Optional<Field> getField(Predicate<Field> predicate) {
        try {
            return Stream.of(this.clazz.getFields())
                    .filter(predicate)
                    .findAny();
        } catch (SecurityException e) {
            this.consumer.accept(e);
        }
        return Optional.empty();
    }

    public List<Field> getFields() {
        try {
            return Stream.of(this.clazz.getFields())
                    .toList();
        } catch (SecurityException e) {
            this.consumer.accept(e);
        }
        return Collections.emptyList();
    }

    public List<Field> getFields(Predicate<Field> predicate) {
        try {
            return Stream.of(this.clazz.getFields())
                    .filter(predicate)
                    .toList();
        } catch (SecurityException e) {
            this.consumer.accept(e);
        }
        return Collections.emptyList();
    }

}
