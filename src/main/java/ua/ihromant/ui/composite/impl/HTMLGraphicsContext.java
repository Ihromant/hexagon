package ua.ihromant.ui.composite.impl;

import lombok.Getter;
import org.teavm.jso.JSObject;
import org.teavm.jso.canvas.CanvasRenderingContext2D;
import org.teavm.jso.canvas.ImageData;
import org.teavm.jso.core.JSArray;
import org.teavm.jso.core.JSNumber;
import ua.ihromant.domain.TextColor;
import ua.ihromant.ui.Color;
import ua.ihromant.ui.HTMLUtil;
import ua.ihromant.ui.composite.GraphicsContext;
import ua.ihromant.ui.composite.ImgData;

import java.util.Arrays;

public class HTMLGraphicsContext implements GraphicsContext {
    @Getter
    private final CanvasRenderingContext2D context;

    public HTMLGraphicsContext(CanvasRenderingContext2D context) {
        this.context = context;
    }

    @Override
    public void setFill(Color color) {
        context.setFillStyle(HTMLUtil.convert(color));
    }

    @Override
    public void setFill(TextColor color) {
        context.setFillStyle(color.name());
    }

    @Override
    public void setStroke(Color color) {
        context.setStrokeStyle(HTMLUtil.convert(color));
    }

    @Override
    public void setStroke(TextColor color) {
        context.setStrokeStyle(color.name());
    }

    @Override
    public void setLineDash(int[] arr) {
        context.setLineDash(JSArray.of(Arrays.stream(arr).mapToObj(JSNumber::valueOf).toArray(JSObject[]::new)));
    }

    @Override
    public void line(int fx, int fy, int tx, int ty) {
        context.beginPath();
        context.moveTo(fx, fy);
        context.lineTo(tx, ty);
        context.stroke();
    }

    @Override
    public void hLine(int y, int fx, int tx) {
        context.beginPath();
        context.moveTo(fx, y);
        context.lineTo(tx, y);
        context.stroke();
    }

    @Override
    public void vLine(int x, int fy, int ty) {
        context.beginPath();
        context.moveTo(x, fy);
        context.lineTo(x, ty);
        context.stroke();
    }

    @Override
    public void circle(int x, int y, int r) {
        context.beginPath();
        context.arc(x, y, r, 0, 2 * Math.PI);
        context.fill();
    }

    @Override
    public void fillRect(int x, int y, int w, int h) {
        context.fillRect(x, y, w, h);
    }

    @Override
    public void putImageData(ImgData data, int dx, int dy) {
        context.putImageData(ImageData.create(((HTMLImgData) data).arr(), data.width()), dx, dy);
    }

    @Override
    public void clearRect(int x, int y, int w, int h) {
        context.clearRect(x, y, w, h);
    }

    @Override
    public void text(String text, int x, int y) {
        context.fillText(text, x, y);
    }
}
