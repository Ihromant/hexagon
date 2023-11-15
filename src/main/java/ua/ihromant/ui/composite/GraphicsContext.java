package ua.ihromant.ui.composite;

import org.teavm.jso.canvas.TextMetrics;
import ua.ihromant.domain.Point;
import ua.ihromant.domain.TextColor;
import ua.ihromant.ui.Color;

public interface GraphicsContext {
    void setFill(Color color);

    void setFill(TextColor color);

    void setStroke(Color color);

    void setStroke(TextColor color);

    void setLineDash(int[] arr);

    void line(int fx, int fy, int tx, int ty);

    void hLine(int y, int fx, int tx);

    void vLine(int x, int fy, int ty);

    void circle(double x, double y, int r);

    void bezier(Point p0, Point p1, Point p2, Point p3);

    void fillRect(int x, int y, int w, int h);

    void putImageData(ImgData data, int dx, int dy);

    void clearRect(int x, int y, int w, int h);

    void text(String text, int x, int y);

    TextMetrics measureText(String text);
}
