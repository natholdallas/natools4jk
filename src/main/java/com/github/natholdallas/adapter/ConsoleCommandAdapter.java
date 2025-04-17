package com.github.natholdallas.adapter;

import snw.jkook.command.ConsoleCommandSender;

public interface ConsoleCommandAdapter {

    boolean preHandle(ConsoleCommandSender sender, Object[] arguments);

    default void afterHandle(ConsoleCommandSender sender, Object[] arguments) {
    }

}
