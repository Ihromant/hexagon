package ua.ihromant.ui.composite.impl;

import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLOptionElement;
import org.teavm.jso.dom.html.HTMLSelectElement;
import ua.ihromant.ui.SelectEvent;
import ua.ihromant.ui.composite.Component;
import ua.ihromant.ui.composite.Select;
import ua.ihromant.ui.composite.SelectOption;

import java.util.function.Consumer;

public class HTMLSelect extends HTMLComponent<HTMLSelectElement> implements Select {
    public HTMLSelect(HTMLSelectElement elem) {
        super(elem);
    }

    @Override
    public Select setWidth(int width) {
        getElem().getStyle().setProperty("width", width + "px");
        return this;
    }

    @Override
    public String getSelected() {
        return getElem().getValue();
    }

    @Override
    public Select setSelected(String value) {
        getElem().setValue(value);
        return this;
    }

    @Override
    public Select setDisabled(boolean disabled) {
        getElem().setDisabled(disabled);
        return this;
    }

    @Override
    public int getIndex() {
        return getElem().getSelectedIndex();
    }

    @Override
    public Select setIndex(int index) {
        getElem().setSelectedIndex(index);
        return this;
    }

    @Override
    public SelectOption[] getOptions() {
        SelectOption[] result = new SelectOption[getElem().getSize()];
        for (int i = 0; i < result.length; i++) {
            HTMLOptionElement option = getElem().getOptions().item(i);
            result[i] = new SelectOption(option.getValue(), option.getText());
        }
        return result;
    }

    @Override
    public Select setOptions(SelectOption[] options) {
        String selected = getSelected();
        boolean found = false;
        getElem().clear();
        for (SelectOption option : options) {
            HTMLOptionElement elem = HTMLDocument.current().createElement("option").cast();
            elem.setValue(option.value());
            elem.setText(option.name());
            if (option.value().equals(selected)) {
                elem.setSelected(true);
                found = true;
            }
            getElem().appendChild(elem);
        }
        if (!found) {
            getElem().setSelectedIndex(0);
        }
        return this;
    }

    @Override
    public void removeOption(String value) {
        for (int i = 0; i < getElem().getSize(); i++) {
            HTMLOptionElement option = getElem().getOptions().item(i);
            if (value.equals(option.getValue())) {
                if (option.isSelected()) {
                    getElem().setSelectedIndex(0);
                }
                option.delete();
                return;
            }
        }
    }

    @Override
    public void addOption(SelectOption option) {
        HTMLOptionElement elem = HTMLDocument.current().createElement("option").cast();
        elem.setValue(option.value());
        elem.setText(option.name());
        getElem().appendChild(elem);
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
