package nathol.jkook.tools.adapter;

import snw.jkook.command.CommandSender;
import snw.jkook.message.Message;

public interface CommandExecutorInterceptAdapter {

    default boolean preHandle(CommandSender sender, Object[] arguments, Message message) {
        return true;
    }

    default void afterHandle(CommandSender sender, Object[] arguments, Message message) {}

}
