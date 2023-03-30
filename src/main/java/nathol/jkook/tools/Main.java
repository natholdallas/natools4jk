package nathol.jkook.tools;

import nathol.jkook.tools.carder.CardSession;

public class Main {
    public static void main(String[] args) {
        CardSession cardSession = new CardSession("test.xml");
        cardSession.card();
    }
}
