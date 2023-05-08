package ua.ihromant.ui.composite.impl;

import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.html.HTMLInputElement;
import ua.ihromant.ui.composite.Checkbox;

public class HTMLCheckbox extends HTMLComponent<HTMLElement> implements Checkbox {
    private final HTMLInputElement input;

    public HTMLCheckbox(HTMLElement label, HTMLInputElement input) {
        super(label);
        this.input = input;
    }

    @Override
    public Checkbox setChecked(boolean checked) {
        input.setChecked(checked);
        return this;
    }

    @Override
    public boolean isChecked() {
        return input.isChecked();
    }
}
