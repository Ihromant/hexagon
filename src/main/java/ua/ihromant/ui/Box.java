package ua.ihromant.ui;

public record Box(int margin, Border border, int padding) {
    public static Box border(Border border) {
        return new Box(0, border, 0);
    }

    public static Box padding(int padding) {
        return new Box(0, null, padding);
    }

    public static Box of(Border border, int padding) {
        return new Box(0, border, padding);
    }

    public static Box of(int margin, Border border, int padding) {
        return new Box(margin, border, padding);
    }
}
