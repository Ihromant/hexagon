package ua.ihromant.ui;

public interface MouseEvt {
    Type type();

    int clientX();

    int clientY();

    int offsetX();

    int offsetY();

    void stop();

    enum Type {
        OVER, OUT, MOVE, CLICK
    }
}
