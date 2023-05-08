package ua.ihromant.ui.composite;

public interface Rectangle {
    int getX();
    int getY();
    int getWidth();
    int getHeight();

    default boolean contains(int x, int y) {
        return x >= getX() && y >= getY() && x < getX() + getWidth() && y < getY() + getHeight();
    }
}
