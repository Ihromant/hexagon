package ua.ihromant.ui.composite;

import ua.ihromant.ui.Color;

public interface ImgData {
    Color pixel(int x, int y);

    void pixel(Color color, int x, int y);

    int width();

    int height();
}
