package nathol.jkook.tools.core.handler;

import org.jetbrains.annotations.Nullable;

import nathol.jkook.tools.adapter.CommandExecutorInterceptAdapter;
import snw.jkook.command.CommandExecutor;
import snw.jkook.command.CommandSender;
import snw.jkook.message.Message;

public final class CommandExecutorHandler implements CommandExecutor {

    private final CommandExecutor executor;
    private final CommandExecutorInterceptAdapter adapter;

    public CommandExecutorHandler(CommandExecutor executor, CommandExecutorInterceptAdapter adapter) {
        this.executor = executor;
        this.adapter = adapter;
    }

    @Override
    public void onCommand(CommandSender sender, Object[] arguments, @Nullable Message message) {
        if (adapter.preHandle(sender, arguments, message)) {
            executor.onCommand(sender, arguments, message);
        }
        adapter.afterHandle(sender, arguments, message);
    }

}
