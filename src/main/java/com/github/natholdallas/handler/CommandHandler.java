package com.github.natholdallas.handler;

import com.github.natholdallas.adapter.CommandAdapter;
import snw.jkook.command.CommandExecutor;
import snw.jkook.command.CommandSender;
import snw.jkook.message.Message;

public final class CommandHandler implements CommandExecutor {

    private final CommandExecutor executor;
    private final CommandAdapter adapter;

    public CommandHandler(CommandExecutor executor, CommandAdapter adapter) {
        this.executor = executor;
        this.adapter = adapter;
    }

    @Override
    public void onCommand(CommandSender sender, Object[] arguments, Message message) {
        if (adapter.preHandle(sender, arguments, message)) {
            executor.onCommand(sender, arguments, message);
        }
        adapter.onComplete(sender, arguments, message);
    }

}
