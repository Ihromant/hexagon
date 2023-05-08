package ua.ihromant.ui.composite.impl;

import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.html.HTMLInputElement;
import org.teavm.jso.dom.html.HTMLSelectElement;
import ua.ihromant.ui.SelectEvent;
import ua.ihromant.ui.composite.Component;
import ua.ihromant.ui.composite.RadioImage;

import java.util.function.Consumer;

public class HTMLRadioImage extends HTMLComponent<HTMLElement> implements RadioImage {
    private final HTMLInputElement input;

    public HTMLRadioImage(HTMLElement label, HTMLInputElement input) {
        super(label);
        this.input = input;
    }

    @Override
    public RadioImage check() {
        input.setChecked(true);
        return this;
    }

    @Override
    public RadioImage setDisabled(boolean disabled) {
        input.setDisabled(disabled);
        return this;
    }

    @Override
    public Component addEventListener(String type, Consumer<?> listener) {
        if ("select".equals(type)) {
            getElem().addEventListener("change", (Event e) -> gen(listener)
                    .accept(new SelectEvent(e.getTarget().<HTMLSelectElement>cast().getValue())));
            return this;
        }
        return super.addEventListener(type, listener);
    }
}
