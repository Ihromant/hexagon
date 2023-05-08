package ua.ihromant.ui.composite.impl;

import org.teavm.jso.dom.html.HTMLInputElement;
import ua.ihromant.ui.composite.Input;

public class HTMLInput extends HTMLComponent<HTMLInputElement> implements Input {
    public HTMLInput(HTMLInputElement elem) {
        super(elem);
    }

    @Override
    public Input setDisabled(boolean disabled) {
        getElem().setDisabled(disabled);
        return this;
    }

    @Override
    public Input setValue(String text) {
        getElem().setValue(text);
        return this;
    }

    @Override
    public String getValue() {
        return getElem().getValue();
    }
}
