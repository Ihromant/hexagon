package ua.ihromant.ui.composite.impl;

import org.teavm.jso.dom.html.HTMLElement;
import ua.ihromant.Crd;
import ua.ihromant.domain.TextColor;
import ua.ihromant.ui.Border;
import ua.ihromant.ui.Color;
import ua.ihromant.ui.HTMLUtil;
import ua.ihromant.ui.composite.Look;

public class HTMLLook implements Look {
    private final HTMLElement elem;

    public HTMLLook(HTMLElement elem) {
        this.elem = elem;
    }

    @Override
    public void border(Border border) {
        if (border == null) {
            elem.getStyle().removeProperty("border");
        } else {
            elem.getStyle().setProperty("border", border.width() + "px " + border.type() + " " + HTMLUtil.convert(border.color()));
        }
    }

    @Override
    public void setEventTransparent(boolean transparent) {
        if (transparent) {
            elem.getClassList().add("event-transparent");
        } else {
            elem.getClassList().remove("event-transparent");
        }
    }

    @Override
    public Look setPosition(int dx, int dy) {
        if (!elem.getClassList().contains("board")) {
            elem.getClassList().add("positioned");
        }
        elem.getStyle().setProperty("left", dx + "px");
        elem.getStyle().setProperty("top", dy + "px");
        return this;
    }

    @Override
    public Crd getPosition() {
        String left = elem.getStyle().getPropertyValue("left");
        String top = elem.getStyle().getPropertyValue("top");
        if (left.isEmpty() || top.isEmpty()) {
            return null;
        }
        return new Crd(Integer.parseInt(left.substring(0, left.length() - 2)), Integer.parseInt(top.substring(0, top.length() - 2)));
    }

    @Override
    public void setBackground(Color background) {
        if (background == null) {
            elem.getStyle().removeProperty("background");
        } else {
            elem.getStyle().setProperty("background", HTMLUtil.convert(background));
        }
    }

    @Override
    public void setBackground(TextColor tc) {
        if (tc == null) {
            elem.getStyle().removeProperty("background-color");
        } else {
            elem.getStyle().setProperty("background-color", tc.name());
        }
    }
}
