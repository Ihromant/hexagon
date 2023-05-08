package ua.ihromant.ui.composite.impl;

import org.teavm.jso.dom.html.HTMLInputElement;
import ua.ihromant.ui.composite.TextButton;

public class HTMLTextButton extends HTMLComponent<HTMLInputElement> implements TextButton {
    public HTMLTextButton(HTMLInputElement elem) {
        super(elem);
    }

    @Override
    public TextButton setDisabled(boolean disabled) {
        getElem().setDisabled(disabled);
        return this;
    }

    @Override
    public TextButton setText(String text) {
        getElem().setValue(text);
        return this;
    }

    @Override
    public TextButton setFontSize(int size) {
        getElem().getStyle().setProperty("font-size", size + "px");
        return this;
    }
}
