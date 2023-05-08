package ua.ihromant.ui.composite;

import ua.ihromant.Crd;
import ua.ihromant.ui.Border;
import ua.ihromant.ui.Color;

public interface Look {
    void border(Border border);

    void setEventTransparent(boolean transparent);

    Look setPosition(int dx, int dy);

    Crd getPosition();

    void setBackground(Color background);
}
