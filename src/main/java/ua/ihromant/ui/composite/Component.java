package ua.ihromant.ui.composite;

import ua.ihromant.Crd;
import ua.ihromant.ui.Box;

import java.util.function.Consumer;

public interface Component {
    void detach();

    Component setVisible(boolean visible);

    Component size(int width, int height);

    Component position(int dx, int dy);

    default Component position(Crd p) {
        return position(p.x(), p.y());
    }

    Component box(Box box);

    void addBefore(Component comp);

    Look look();

    Rectangle bounds();

    Component addEventListener(String type, Consumer<?> listener);
}
