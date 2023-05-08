package ua.ihromant;

public record Point(double x, double y) {
    public Point add(Point that) {
        return new Point(this.x + that.x, this.y + that.y);
    }
}

