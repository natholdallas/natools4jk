package nathol.jkook.tools.repository;

import java.util.Collection;

import snw.jkook.command.JKookCommand;
import snw.jkook.command.OptionalArgumentContainer;

public final class CommandUltimateRegistry {

    private final JKookCommand jKookCommand = new JKookCommand("j");

    public void addArgument(Class<?> clazz) {
        this.jKookCommand.addArgument(clazz);
    }

    public <T> void addOptionalArgument(Class<T> clazz, T defaultValue) {
        this.jKookCommand.addOptionalArgument(clazz, defaultValue);
    }

    public OptionalArgumentContainer getOptionalArgumentContainer() {
        return this.jKookCommand.getOptionalArguments();
    }

    public Collection<Class<?>> getArguments() {
        return this.jKookCommand.getArguments();
    }

}
