package ua.ihromant.ui.composite;

import ua.ihromant.ui.Color;

public interface GraphicsContext {
    void setFill(Color color);

    void setStroke(Color color);

    void setLineDash(int[] arr);

    void line(int fx, int fy, int tx, int ty);

    void hLine(int y, int fx, int tx);

    void vLine(int x, int fy, int ty);

    void circle(int x, int y, int r);

    void fillRect(int x, int y, int w, int h);

    void putImageData(ImgData data, int dx, int dy);

    void clearRect(int x, int y, int w, int h);

    void text(String text, int x, int y);
}
