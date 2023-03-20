package nathol.jkook.tools.natools.card.module;

import snw.jkook.message.component.card.Theme;
import snw.jkook.message.component.card.element.ButtonElement;
import snw.jkook.message.component.card.element.ButtonElement.EventType;
import snw.jkook.message.component.card.element.InteractElement;
import snw.jkook.message.component.card.element.MarkdownElement;
import snw.jkook.message.component.card.element.PlainTextElement;

import java.util.LinkedList;
import java.util.List;

public class InteractBuilder {
    private final List<InteractElement> list;

    public InteractBuilder(){
        list = new LinkedList<>();
    }

    public InteractBuilder addButton(Theme theme, String value, EventType type, MarkdownElement markdownElement){
        list.add(new ButtonElement(theme, value, type, markdownElement));
        return this;
    }

    public InteractBuilder addButton(Theme theme, MarkdownElement markdownElement){
        list.add(new ButtonElement(theme, "non", EventType.NO_ACTION, markdownElement));
        return this;
    }

    public InteractBuilder addButton(Theme theme, String value, EventType type, PlainTextElement plainTextElement){
        list.add(new ButtonElement(theme, value, type, plainTextElement));
        return this;
    }

    public InteractBuilder addButton(Theme theme, PlainTextElement plainTextElement){
        list.add(new ButtonElement(theme, "non", EventType.NO_ACTION, plainTextElement));
        return this;
    }

    public List<InteractElement> build() {
        return list;
    }
}
