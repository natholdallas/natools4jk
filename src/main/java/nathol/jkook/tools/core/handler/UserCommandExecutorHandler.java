package nathol.jkook.tools.core.handler;

import org.jetbrains.annotations.Nullable;

import nathol.jkook.tools.adapter.UserCommandExecutorInterceptAdapter;
import snw.jkook.command.UserCommandExecutor;
import snw.jkook.entity.User;
import snw.jkook.message.Message;

public class UserCommandExecutorHandler implements UserCommandExecutor {

    private final UserCommandExecutor executor;
    private final UserCommandExecutorInterceptAdapter adapter;

    public UserCommandExecutorHandler(UserCommandExecutor executor, UserCommandExecutorInterceptAdapter adapter) {
        this.executor = executor;
        this.adapter = adapter;
    }

    @Override
    public void onCommand(User sender, Object[] arguments, @Nullable Message message) {
        if (this.adapter.preHandle(sender, arguments, message)) {
            executor.onCommand(sender, arguments, message);
        }
        this.adapter.afterHandle(sender, arguments, message);
    }

}
