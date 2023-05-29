package ua.ihromant.jso;

import org.teavm.jso.JSObject;
import org.teavm.jso.JSProperty;

public interface TextMetrics extends JSObject {
    @JSProperty
    double getWidth();

    @JSProperty
    double getActualBoundingBoxAscent();

    @JSProperty
    double getActualBoundingBoxDescent();

    @JSProperty
    double getActualBoundingBoxLeft();

    @JSProperty
    double getActualBoundingBoxRight();

    @JSProperty
    double getFontBoundingBoxAscent();

    @JSProperty
    double getFontBoundingBoxDescent();
}

