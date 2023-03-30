package nathol.jkook.tools.utils;

import java.util.Random;

import snw.jkook.message.component.card.Theme;
import snw.jkook.message.component.card.element.MarkdownElement;
import snw.jkook.message.component.card.element.PlainTextElement;

/* usage: import static package name + class name */
public class EasyBuilder {
    public static Theme randowTheme(){
        return Theme.values()[new Random().nextInt(1, Theme.values().length)];
    }

    /* a method to easy build PlainTextElement */
    public static PlainTextElement plainText(String content, boolean emoji){
        return new PlainTextElement(content, emoji);
    }
    public static PlainTextElement plainText(String content){
        return new PlainTextElement(content);
    }

    /* a method to easy build MarkdownElement */
    public static MarkdownElement markdown(String content){
        return new MarkdownElement(content);
    }
}
