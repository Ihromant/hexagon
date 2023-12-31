package ua.ihromant.domain;

import java.util.Arrays;
import java.util.BitSet;
import java.util.concurrent.ThreadLocalRandom;

public interface Visualizer {
    double alpha = 0.5;

    Point coordinate(int p);

    BitSet[] lines();

    BitSet points();

    default Point[][] bezier(BitSet line) {
        Point[] points = line.stream().mapToObj(this::coordinate).toArray(Point[]::new);
        Point[][] result = new Point[points.length - 1][];
        result[0] = bezierForPoints(points[0].add(points[0].subtract(points[1]).mul(0.001)), points[0], points[1], points[2]);
        for (int i = 0; i < points.length - 3; i++) {
            result[i + 1] = bezierForPoints(points[i], points[i + 1], points[i + 2], points[i + 3]);
        }
        result[points.length - 2] = bezierForPoints(points[points.length - 3], points[points.length - 2], points[points.length - 1],
                points[points.length - 1].add(points[points.length - 1].subtract(points[points.length - 2]).mul(0.001)));
        return result;
    }

    default LineData[] model() {
        return Arrays.stream(F9Point.generateUnital()).map(bs -> new LineData(bs,
                TextColor.values()[ThreadLocalRandom.current().nextInt(TextColor.values().length - 2)], bezier(bs))).toArray(LineData[]::new);
    }

    private static Point[] bezierForPoints(Point p0, Point p1, Point p2, Point p3) {
        double d1 = Math.pow(p1.dist(p0), alpha);
        double d2 = Math.pow(p2.dist(p1), alpha);
        double d3 = Math.pow(p3.dist(p2), alpha);
        Point t1;
        Point t2;
        // Modify tangent 1
        {
            double a = d1 * d1;
            double b = d2 * d2;
            double c = (2 * d1 * d1) + (3 * d1 * d2) + (d2 * d2);
            double d = 3 * d1 * (d1 + d2);
            double t1x = (a * p2.x() - b * p0.x() + c * p1.x()) / d;
            double t1y = (a * p2.y() - b * p0.y() + c * p1.y()) / d;
            t1 = new Point(t1x, t1y);
        }
        // Modify tangent 2
        {
            double a = d3 * d3;
            double b = d2 * d2;
            double c = (2 * d3 * d3) + (3 * d3 * d2) + (d2 * d2);
            double d = 3 * d3 * (d3 + d2);
            double t2x = (a * p1.x() - b * p3.x() + c * p2.x()) / d;
            double t2y = (a * p1.y() - b * p3.y() + c * p2.y()) / d;
            t2 = new Point(t2x, t2y);
        }
        return new Point[]{p1, t1, t2, p2};
    }

}
