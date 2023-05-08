package ua.ihromant.ui;

public interface Color {
    Color SELECTION = new RGBAColor(247, 222, 123);
    Color TRANSPARENT = new RGBAColor(0, 0, 0, 0);
    Color TRANS_GRAY = new RGBAColor(128, 128, 128, 0.5);
    Color GRAY = new RGBAColor(128, 128, 128);
    Color WHITE = new RGBAColor(255, 255, 255);
    Color BLACK = new RGBAColor(0, 0, 0);
    Color FRENCH_ROSE = new RGBAColor(255, 75, 125);

    Color RED_FLAG = new RGBAColor(203, 24, 24);
    Color TAN_FLAG = new RGBAColor(222, 181, 148);
    Color ORANGE_FLAG = new RGBAColor(214, 99, 0);
    Color TEAL_FLAG = new RGBAColor(79, 145, 153);
    Color PINK_FLAG = new RGBAColor(178, 107, 123);

    Color RED = new RGBAColor(255, 0, 0);
    Color BLUE = new RGBAColor(0, 0, 255);
    Color GREEN = new RGBAColor(0, 128, 0);
    Color ORANGE = new RGBAColor(255, 165, 0);

    Color POSSIBLE = new RGBAColor(149, 69, 53, 0.25);
    Color MOVE = new RGBAColor(149, 69, 53, 0.5);

    Color TOOLTIP_BACKGROUND = new RGBAColor(255, 255, 221);
    Color TOOLTIP_BORDER = new RGBAColor(136, 136, 102);

    int r();
    int g();
    int b();
    double a();
    boolean trans();
}
