package nathol.jkook.tools.adapter;

import snw.jkook.command.ConsoleCommandSender;

public interface ConsoleCommandExecutorInterceptAdapter {

    default boolean preHandle(ConsoleCommandSender sender, Object[] arguments) {
        return true;
    }

    default void afterHandle(ConsoleCommandSender sender, Object[] arguments) {}

}
