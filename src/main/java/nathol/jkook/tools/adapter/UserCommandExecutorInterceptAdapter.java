package nathol.jkook.tools.adapter;

import snw.jkook.entity.User;
import snw.jkook.message.Message;

public interface UserCommandExecutorInterceptAdapter {

    default boolean preHandle(User sender, Object[] arguments, Message message) {
        return true;
    }

    default void afterHandle(User sender, Object[] arguments, Message message) {}

}
