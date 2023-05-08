package ua.ihromant.ui;

public record RGBAColor(int r, int g, int b, double a) implements Color {
    public RGBAColor(int r, int g, int b) {
        this(r, g, b, 1.0);
    }

    @Override
    public boolean trans() {
        return a == 0.0;
    }
}
