package nathol.jkook.tools.carder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import snw.jkook.message.component.card.CardBuilder;

public class CardSession {
    private final CardBuilder cardBuilder = new CardBuilder();
    private final Document dom;

    public CardSession(String path) {
        InputStream inputStream = CardSession.class.getClassLoader().getResourceAsStream(path);
        try {
            this.dom = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
            return;
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new RuntimeException("Dom hash been down");
        }
    }

    public void nodeList(NodeList nodeList) {}

    public void card() {
        Element element = dom.getDocumentElement();
        List<Node> cards = convertList(element.getChildNodes());
        System.out.println(cards.size());
        cards.forEach(k -> {
            List<Node> childNodes = convertList(k.getChildNodes());
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
