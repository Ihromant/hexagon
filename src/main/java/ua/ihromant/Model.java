package ua.ihromant;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Model {
    private int counter;
    private final Map<Integer, ColorPoint> points = new HashMap<>();
    private final Map<Edge, ColorEdge> edges = new HashMap<>();

    public Optional<ColorPoint> find(Point to) {
        return points.values().stream().filter(cp -> cp.getPoint().dist(to) < 0.3).findAny();
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
}
