package ua.ihromant.ui.composite.impl;

import org.teavm.jso.dom.html.HTMLElement;
import ua.ihromant.ui.Color;
import ua.ihromant.ui.HTMLUtil;
import ua.ihromant.ui.composite.Text;

public class HTMLText extends HTMLComponent<HTMLElement> implements Text {
    private Color color;
    private String value;

    public HTMLText(HTMLElement elem) {
        super(elem);
        clear();
    }

    @Override
    public Text setText(String text) {
        return text == null ? clear() : setValue(text);
    }

    @Override
    public Text setText(Integer text) {
        return text == null ? clear() : setValue(text.toString());
    }

    @Override
    public Text clear() {
        getElem().getStyle().setProperty("color", "transparent");
        getElem().setInnerHTML("Plh");
        return this;
    }

    @Override
    public Text setFontSize(int size) {
        getElem().getStyle().setProperty("font-size", size + "px");
        return this;
    }

    @Override
    public Text setColor(Color color) {
        this.color = color;
        if (value != null) {
            getElem().getStyle().setProperty("color", HTMLUtil.convert(color));
        } else {
            getElem().getStyle().setProperty("color", "transparent");
        }
        return this;
    }

    @Override
    public Text centered() {
        getElem().getClassList().add("text-center");
        return this;
    }

    @Override
    public Text background(Color background) {
        look().setBackground(background);
        return this;
    }

    @Override
    public Text scroll(int height) {
        getElem().getStyle().setProperty("height", height + "px");
        getElem().getClassList().add("ver-scroll");
        return this;
    }

    private Text setValue(String value) {
        this.value = value;
        if (color != null) {
            getElem().getStyle().setProperty("color", HTMLUtil.convert(color));
        } else {
            getElem().getStyle().removeProperty("color");
        }
        getElem().setInnerHTML(value);
        return this;
    }
}
