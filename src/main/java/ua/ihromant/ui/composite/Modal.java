package ua.ihromant.ui.composite;

public interface Modal {
    void show(Component comp);

    void hide();

    boolean hidable();
}
