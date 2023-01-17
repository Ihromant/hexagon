package ua.ihromant;

import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLElement;

public class Client {
    public static void main(String[] args) {
        HTMLDocument document = HTMLDocument.current();
        HTMLElement div = HTMLDocument.current().createElement("div").cast();
        div.setInnerText("Hello, World!");
        document.getBody().appendChild(div);
    }
}
