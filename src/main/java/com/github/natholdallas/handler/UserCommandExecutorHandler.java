package com.github.natholdallas.handler;

import com.github.natholdallas.adapter.UserCommandAdapter;
import org.jetbrains.annotations.Nullable;

import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.User;
import snw.jkook.message.Message;

public class UserCommandExecutorHandler implements UserCommandExecutor {

    private final UserCommandExecutor executor;
    private final UserCommandAdapter adapter;

    public UserCommandExecutorHandler(UserCommandExecutor executor, UserCommandAdapter adapter) {
        this.executor = executor;
        this.adapter = adapter;
    }

    @Override
    public void onCommand(User sender, Object[] arguments, @Nullable Message message) {
        if (this.adapter.preHandle(sender, arguments, message)) {
            executor.onCommand(sender, arguments, message);
        }
        this.adapter.onComplete(sender, arguments, message);
    }

}
