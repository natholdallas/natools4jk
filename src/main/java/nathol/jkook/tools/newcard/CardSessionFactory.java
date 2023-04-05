package nathol.jkook.tools.newcard;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class CardSessionFactory {
    private CardSessionFactory() {
    }

    public static CardSession resourceToCard(String path) {
        Document document = null;
        try (InputStream inputStream = CardSessionFactory.class.getClassLoader().getResourceAsStream(path)) {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new RuntimeException("Dom hash been down");
        }
        return new CardSession(document.getDocumentElement().getChildNodes());
    }

    public static CardSession fileToCard(String path) {
        Document document = null;
        try (InputStream inputStream = new FileInputStream(new File(path))) {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            throw new RuntimeException();
        }
        return new CardSession(document.getDocumentElement().getChildNodes());
    }
}
