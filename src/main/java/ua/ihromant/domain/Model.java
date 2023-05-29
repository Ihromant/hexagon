package ua.ihromant.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Model {
    private static final double distEps = 0.3;
    private static final double angleEps = 0.2;
    private final Map<Integer, ColorPoint> points = new HashMap<>();
    private final Map<Edge, ColorEdge> edges = new HashMap<>();
    private double angle;
    private int counter;

    public Optional<ColorPoint> findPoint(Point to) {
        return points.values().stream().filter(cp -> cp.getPoint().dist(to) < distEps).findAny();
    }

    public void addOrReplace(ColorPoint cp) {
        if (points.put(cp.getId(), cp) == null) {
            counter++;
        }
    }

    public void addOrReplace(ColorEdge edge) {
        edges.put(edge.getEdge(), edge);
    }

    public int counter() {
        return counter;
    }

    public Iterable<ColorPoint> colorPoints() {
        return points.values();
    }

    public ColorPoint byId(int id) {
        return points.get(id);
    }

    public Iterable<ColorEdge> colorEdges() {
        return edges.values();
    }

    private boolean isBetween(Point begin, Point end, Point between) {
        Point fromV = begin.subtract(between);
        Point toV = end.subtract(between);
        double absF = fromV.abs();
        double absT = toV.abs();
        if (absF < distEps || absT < distEps) {
            return false;
        }
        double sin = fromV.vector(toV) / absF / absT;
        if (Math.abs(sin) > angleEps) {
            return false;
        }
        return fromV.scalar(toV) <= 0;
    }

    public Optional<ColorEdge> findEdge(Point clicked) {
        for (Map.Entry<Integer, ColorPoint> from : points.entrySet()) {
            for (Map.Entry<Integer, ColorPoint> to : points.entrySet()) {
                if (from.getKey() >= to.getKey()) {
                    continue;
                }
                ColorEdge ce = edges.get(Edge.from(from.getKey(), to.getKey()));
                if (ce != null && isBetween(from.getValue().getPoint(), to.getValue().getPoint(), clicked)) {
                    return Optional.of(ce);
                }
            }
        }
        return Optional.empty();
    }

    public void setAngle(double angle) {
        double diff = this.angle - angle;
        points.values().forEach(v -> v.setPoint(v.getPoint().rotateHyperbolic(diff)));
        this.angle = angle;
    }
}
