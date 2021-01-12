package ua.ihromant;

public class Point {
    private final double x;
    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point add(Point other) {
        return new Point(this.x + other.x, this.y + other.y);
    }

    @Override
    public String toString() {
        return x + "," + y;
        // TODO uncomment to see bottleneck disappears return (int) x + "," + (int) y;
    }
}
