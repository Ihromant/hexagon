package ua.ihromant.domain;

public record Point(double x, double y) {
    public Point add(Point that) {
        return new Point(this.x + that.x, this.y + that.y);
    }

    public Point subtract(Point that) {
        return new Point(this.x - that.x, this.y - that.y);
    }

    public double dist(Point that) {
        return Math.hypot(that.x - this.x, that.y - this.y);
    }

    public double scalar(Point that) {
        return this.x * that.x + this.y * that.y;
    }

    public double vector(Point that) {
        return this.x * that.y - that.x * this.y;
    }

    public double abs() {
        return Math.hypot(x, y);
    }

    public Point rotateHyperbolic(double delta) {
        double coh = Math.cosh(delta);
        double sih = Math.sinh(delta);
        return new Point(this.y * sih + this.x * coh, this.y * coh + this.x * sih);
    }
}

