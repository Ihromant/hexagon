package ua.ihromant;

import ua.ihromant.ui.HTMLUtil;

public record Crd(int x, int y) {
    public Crd(double x, double y) {
        this(HTMLUtil.round(x), HTMLUtil.round(y));
    }
    public Crd add(Crd other) {
        return new Crd(this.x + other.x, this.y + other.y);
    }
}
