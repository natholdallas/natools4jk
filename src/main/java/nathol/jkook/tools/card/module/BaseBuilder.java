package nathol.jkook.tools.card.module;

import snw.jkook.message.component.card.Size;
import snw.jkook.message.component.card.element.BaseElement;
import snw.jkook.message.component.card.element.ImageElement;
import snw.jkook.message.component.card.element.MarkdownElement;
import snw.jkook.message.component.card.element.PlainTextElement;

import java.util.LinkedList;
import java.util.List;

public class BaseBuilder {
    private final List<BaseElement> list;

    public BaseBuilder(){
        list = new LinkedList<>();
    }

    public BaseBuilder addMarkdown(String content) {
        list.add(new MarkdownElement(content));
        return this;
    }

    public BaseBuilder addPlainText(String content, boolean emoji) {
        list.add(new PlainTextElement(content, emoji));
        return this;
    }
    public BaseBuilder addPlainText(String content) {
        list.add(new PlainTextElement(content));
        return this;
    }

    public BaseBuilder addImage(String src, String alt, Size size, boolean circle) {
        list.add(new ImageElement(src, alt, size, circle));
        return this;
    }
    public BaseBuilder addImage(String src, String alt, boolean circle) {
        list.add(new ImageElement(src, alt, circle));
        return this;
    }

    public List<BaseElement> build() {
        return list;
    }
}
