package ua.ihromant.ui.composite.impl;

import org.teavm.jso.dom.html.HTMLElement;
import ua.ihromant.ui.composite.Component;
import ua.ihromant.ui.composite.Container;

public class HTMLContainer extends HTMLComponent<HTMLElement> implements Container {
    public HTMLContainer(HTMLElement elem) {
        super(elem);
    }

    @Override
    public HTMLContainer add(Component comp) {
        getElem().appendChild(((HTMLComponent<?>) comp).getElem());
        return this;
    }

    @Override
    public void clear() {
        getElem().clear();
    }

    @Override
    public Container scene() {
        getElem().getClassList().add("scene");
        return this;
    }
}
