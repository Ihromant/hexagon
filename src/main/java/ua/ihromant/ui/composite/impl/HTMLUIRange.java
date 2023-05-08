package ua.ihromant.ui.composite.impl;

import org.teavm.jso.dom.html.HTMLInputElement;
import ua.ihromant.ui.composite.UIRange;

import java.util.Optional;
import java.util.function.IntConsumer;

public class HTMLUIRange extends HTMLComponent<HTMLInputElement> implements UIRange {
    private IntConsumer listener;

    public HTMLUIRange(HTMLInputElement range) {
        super(range);
        range.addEventListener("change", e -> onChange());
    }

    @Override
    public UIRange setRange(int min, int max) {
        getElem().setAttribute("min", String.valueOf(min));
        getElem().setAttribute("max", String.valueOf(max));
        return this;
    }

    private void onChange() {
        int newVal = Integer.parseInt(getElem().getValue());
        Optional.ofNullable(listener).ifPresent(l -> l.accept(newVal));
    }

    @Override
    public int value() {
        return Integer.parseInt(getElem().getValue());
    }

    @Override
    public int min() {
        return Integer.parseInt(getElem().getAttribute("min"));
    }

    @Override
    public int max() {
        return Integer.parseInt(getElem().getAttribute("max"));
    }

    @Override
    public UIRange setValue(int newValue) {
        getElem().setValue(String.valueOf(newValue));
        return this;
    }

    @Override
    public UIRange setListener(IntConsumer listener) {
        this.listener = listener;
        return this;
    }
}
