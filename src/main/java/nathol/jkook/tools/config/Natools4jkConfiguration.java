package nathol.jkook.tools.config;

import java.util.Collections;
import java.util.List;
import nathol.jkook.tools.adapter.CommandExecutorInterceptAdapter;
import nathol.jkook.tools.adapter.ConsoleCommandExecutorInterceptAdapter;
import nathol.jkook.tools.adapter.ListenerInterceptAdapter;
import nathol.jkook.tools.adapter.UserCommandExecutorInterceptAdapter;

public class Natools4jkConfiguration {

    private final List<Class<? extends CommandExecutorInterceptAdapter>> commandInterceptAdapters;
    private final List<Class<? extends UserCommandExecutorInterceptAdapter>> userCommandInterceptAdapters;
    private final List<Class<? extends ConsoleCommandExecutorInterceptAdapter>> consoleCommandInterceptAdapters;
    private final List<Class<? extends ListenerInterceptAdapter>> listenerInterceptAdapters;

    public Natools4jkConfiguration(
        List<Class<? extends CommandExecutorInterceptAdapter>> commandInterceptAdapters,
        List<Class<? extends UserCommandExecutorInterceptAdapter>> userCommandInterceptAdapters,
        List<Class<? extends ConsoleCommandExecutorInterceptAdapter>> consoleCommandInterceptAdapters,
        List<Class<? extends ListenerInterceptAdapter>> listenerInterceptAdapters
    ) {
        this.commandInterceptAdapters = commandInterceptAdapters == null ? Collections.emptyList() : commandInterceptAdapters;
        this.userCommandInterceptAdapters = userCommandInterceptAdapters == null ? Collections.emptyList() : userCommandInterceptAdapters;
        this.consoleCommandInterceptAdapters = consoleCommandInterceptAdapters == null ? Collections.emptyList() : consoleCommandInterceptAdapters;
        this.listenerInterceptAdapters = listenerInterceptAdapters == null ? Collections.emptyList() : listenerInterceptAdapters;
    }

    public List<Class<? extends CommandExecutorInterceptAdapter>> getCommandInterceptAdapters() {
        return commandInterceptAdapters;
    }

    public List<Class<? extends UserCommandExecutorInterceptAdapter>> getUserCommandInterceptAdapters() {
        return userCommandInterceptAdapters;
    }

    public List<Class<? extends ConsoleCommandExecutorInterceptAdapter>> getConsoleCommandInterceptAdapters() {
        return consoleCommandInterceptAdapters;
    }

    public List<Class<? extends ListenerInterceptAdapter>> getListenerInterceptAdapters() {
        return listenerInterceptAdapters;
    }

}
