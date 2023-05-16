package nathol.jkook.tools.core.handler;

import nathol.jkook.tools.adapter.ConsoleCommandExecutorInterceptAdapter;
import snw.jkook.command.ConsoleCommandExecutor;
import snw.jkook.command.ConsoleCommandSender;

public class ConsoleCommandExecutorHandler implements ConsoleCommandExecutor {

    private final ConsoleCommandExecutor executor;
    private final ConsoleCommandExecutorInterceptAdapter adapter;

    public ConsoleCommandExecutorHandler(ConsoleCommandExecutor executor, ConsoleCommandExecutorInterceptAdapter adapter) {
        this.executor = executor;
        this.adapter = adapter;
    }

    @Override
    public void onCommand(ConsoleCommandSender sender, Object[] arguments) {
        if (this.adapter.preHandle(sender, arguments)) {
            this.executor.onCommand(sender, arguments);
        }
        this.adapter.afterHandle(sender, arguments);
    }

}
