package ua.ihromant.domain;

import java.util.Arrays;
import java.util.BitSet;

public class UnitalVisualizer implements Visualizer {
    @Override
    public Point coordinate(int p) {
        if (p < 9 * 9) {
            int x = p / 9;
            int y = p % 9;
            return new Point((x - 4) * 99 + 400, (y - 4) * 99 + 400);
        } else {
            p = p - 9 * 9;
            return new Point(900, (p - 4) * 99 + 400);
        }
    }

    @Override
    public BitSet[] lines() {
        return F9Point.generateUnital();
    }

    @Override
    public BitSet points() {
        return Arrays.stream(F9Point.generateUnital()).flatMapToInt(BitSet::stream).collect(BitSet::new, BitSet::set, BitSet::or);
    }
}
