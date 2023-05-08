package ua.ihromant.ui;

public record Border(Color color, String type, int width, int radius) {
    private static final String SOLID = "solid";
    public static final String INSET = "inset";
    public static final Border BLACK = Border.color(Color.BLACK);
    public static final Border TRANSPARENT = Border.color(Color.TRANSPARENT);
    public static final Border GRAY = Border.color(Color.TRANS_GRAY);
    public static final Border YELLOW = Border.color(Color.SELECTION);
    public static final Border GRAY_INSET = Border.of(Color.GRAY, INSET);

    public static Border color(Color color) {
        return new Border(color, SOLID, 1, 0);
    }

    public static Border of(Color color, String type) {
        return new Border(color, type, 1, 0);
    }

    public static Border of(Color color, int width, int radius) {
        return new Border(color, SOLID, width, radius);
    }
}
