package ua.ihromant;

public record Point(double x, double y) {
    public Point add(Point that) {
        return new Point(this.x + that.x, this.y + that.y);
    }

    public double dist(Point that) {
        return Math.hypot(that.x - this.x, that.y - this.y);
    }
}

