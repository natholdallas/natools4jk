package nathol.jkook.tools.card.module;

import snw.jkook.message.component.card.element.BaseElement;
import snw.jkook.message.component.card.element.MarkdownElement;
import snw.jkook.message.component.card.element.PlainTextElement;
import snw.jkook.message.component.card.structure.Paragraph;

import java.util.Collection;
import java.util.LinkedList;

public class ParagraphBuilder {
    private final int column;
    private final Collection<BaseElement> collection = new LinkedList<>();

    public ParagraphBuilder(int column){
        this.column = column;
    }

    public ParagraphBuilder addMarkdown(String content) {
        collection.add(new MarkdownElement(content));
        return this;
    }

    public ParagraphBuilder addPlainText(String content, boolean emoji) {
        collection.add(new PlainTextElement(content, emoji));
        return this;
    }
    public ParagraphBuilder addPlainText(String content) {
        collection.add(new PlainTextElement(content));
        return this;
    }

    public Paragraph build() {
        return new Paragraph(column, collection);
    }
}
