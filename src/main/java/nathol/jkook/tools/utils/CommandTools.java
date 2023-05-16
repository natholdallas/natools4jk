package nathol.jkook.tools.utils;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;

import snw.jkook.message.Message;
import snw.jkook.message.PrivateMessage;
import snw.jkook.message.TextChannelMessage;

public final class CommandTools {

    public static <T> T[] toArray(Object[] objects, IntFunction<T[]> generator) {
        return Arrays.stream(objects).toArray(generator);
    }

    public static <T> List<T> toList(Object[] objects, IntFunction<T[]> generator) {
        return List.of(Arrays.stream(objects).toArray(generator));
    }

    public static TextChannelMessage castToTextChannelMessage(Message message) {
        return ((TextChannelMessage) message);
    }

    public static PrivateMessage castToPrivateMessage(Message message) {
        return ((PrivateMessage) message);
    }

}
