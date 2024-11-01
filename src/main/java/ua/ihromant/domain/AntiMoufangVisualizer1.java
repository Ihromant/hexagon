package ua.ihromant.domain;

import java.util.Arrays;
import java.util.BitSet;

public class AntiMoufangVisualizer1 implements Visualizer {
    private static final Point center = new Point(500, 400);
    private static final int a0 = 0;
    private static final int a1 = -72;
    private static final int a2 = 72;
    private static final int a3 = -144;
    private static final int a4 = 144;
    @Override
    public Point coordinate(int p) {
        return switch (p) {
            case 0 -> center;
            case 7 -> centerDist(a0, 50);
            case 8 -> centerDist(a0, 100);
            case 10 -> centerDist(a0, 150);
            case 11 -> centerDist(a0, 200);
            case 1 -> centerDist(a1, 100);
            case 2 -> centerDist(a1, 200);
            case 3 -> centerDist(a2, 100);
            case 4 -> centerDist(a2, 200);
            case 5 -> centerDist(a3, 100);
            case 6 -> centerDist(a3, 200);
            case 9 -> centerDist(a4, 100);
            case 12 -> centerDist(a4, 200);
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public BitSet[] lines() {
        return Arrays.stream(new int[][]{
                {0, 5, 6}, {0, 7, 8, 10, 11}, {0, 9, 12}, {1, 5, 8}, {1, 6, 11}, {2, 5, 11}, {2, 6, 8}, {0, 1, 2},
                {3, 5, 9}, {3, 6, 12}, {1, 3, 7}, {2, 3, 10}, {4, 5, 12}, {4, 6, 9}, {1, 4, 10}, {2, 4, 7}, {0, 3, 4}
        }).map(AntiMoufangVisualizer1::of).toArray(BitSet[]::new);
    }

    private static Point centerDist(int angle, int dist) {
        double rad = Math.toRadians(angle);
        Point vec = new Point(Math.sin(rad), Math.cos(rad));
        return center.add(vec.mul(2 * dist));
    }

    @Override
    public BitSet points() {
        BitSet result = new BitSet();
        result.set(0, 13);
        return result;
    }

    private static BitSet of(int... vals) {
        BitSet result = new BitSet();
        for (int val : vals) {
            result.set(val);
        }
        return result;
    }
}
