package nathol.jkook.tools.natools.card.module;

import snw.jkook.message.component.card.Size;
import snw.jkook.message.component.card.element.ImageElement;

import java.util.LinkedList;
import java.util.List;

public class ImageBuilder{
    private final List<ImageElement> list;

    public ImageBuilder(){
        list = new LinkedList<>();
    }

    public ImageBuilder add(String src, String alt, Size size, boolean circle){
        if(verfity()) list.add(new ImageElement(src, alt, size, circle));
        return this;
    }
    public ImageBuilder add(String src, String alt, boolean circle){
        if(verfity()) list.add(new ImageElement(src, alt, circle));
        return this;
    }

    public List<ImageElement> build() {
        return list;
    }

    private boolean verfity(){
        int size = list.size();
        return size <= 9 && size >= 1;
    }
}
