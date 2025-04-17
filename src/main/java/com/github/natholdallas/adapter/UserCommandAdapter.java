package com.github.natholdallas.adapter;

import snw.jkook.entity.User;
import snw.jkook.message.Message;

public interface UserCommandAdapter {

    default boolean preHandle(User sender, Object[] arguments, Message message) {
        return true;
    }

    default void onComplete(User sender, Object[] arguments, Message message) {
    }

}
