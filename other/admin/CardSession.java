package nathol.jkook.tools.admin;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import snw.jkook.message.component.card.CardBuilder;
import snw.jkook.message.component.card.MultipleCardComponent;

public class CardSession {
    private final CardBuilder cardBuilder = new CardBuilder();

    protected CardSession(NodeList cardList) {
        convertList(cardList).forEach(k -> {
            resolverCard(convertList(k.getChildNodes()));
        });
    }

    public MultipleCardComponent build() {
        return this.cardBuilder.build();
    }

    public static void resolverCard(List<Node> modules) {
        modules.forEach(module -> {
            System.out.println(module.getNodeName());
            switch(module.getNodeName()) {}
        });
    }

    public static List<Node> convertList(NodeList nodeList) {
        List<Node> list = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            list.add(nodeList.item(i));
        }
        return list;
    }
}
