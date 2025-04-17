package com.github.natholdallas.adapter;

import snw.jkook.command.CommandSender;
import snw.jkook.message.Message;

public interface CommandAdapter {

    boolean preHandle(CommandSender sender, Object[] arguments, Message message);

    default void onComplete(CommandSender sender, Object[] arguments, Message message) {
    }

}
