package ua.ihromant.ui.composite.impl;

import org.teavm.jso.dom.html.HTMLCanvasElement;
import ua.ihromant.ui.composite.Canvas;
import ua.ihromant.ui.composite.GraphicsContext;

public class HTMLCanvas extends HTMLComponent<HTMLCanvasElement> implements Canvas {
    public HTMLCanvas(HTMLCanvasElement elem) {
        super(elem);
    }

    @Override
    public GraphicsContext getContext() {
        return new HTMLGraphicsContext(getElem().getContext("2d").cast());
    }

    @Override
    public Canvas pixelSize(int width, int height) {
        getElem().setWidth(width);
        getElem().setHeight(height);
        return this;
    }

    @Override
    public int pixelWidth() {
        return getElem().getWidth();
    }

    @Override
    public int pixelHeight() {
        return getElem().getHeight();
    }
}
