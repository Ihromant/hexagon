package ua.ihromant.domain;

import java.util.BitSet;
import java.util.stream.IntStream;

public class STS13Visualizer implements Visualizer {
    public static final STS13Visualizer first = new STS13Visualizer("00000011111222223334445556", "13579b3469a3467867868a7897", "2468ac578bc95acbbacc9bbac9");
    public static final STS13Visualizer second = new STS13Visualizer("00000011111222223334445556", "13579b3469a3467867868a7897", "2468ac578bc95abcbcac9babc9");

    private final BitSet[] lines;
    private STS13Visualizer(String... design) {
        this.lines = IntStream.range(0, design[0].length()).mapToObj(idx -> {
            BitSet res = new BitSet();
            for (String s : design) {
                res.set(Character.digit(s.charAt(idx), 36));
            }
            return res;
        }).toArray((BitSet[]::new));
    }

    @Override
    public Point coordinate(int p) {
        if (p == 0) {
            return new Point(500, 400);
        }
        p = p - 1;
        int r = p % 2 == 0 ? 180 : 360;
        double alpha = (p / 2) * Math.PI / 3;
        return new Point(r * Math.cos(alpha) + 500, r * Math.sin(alpha) + 400);
    }

    @Override
    public BitSet[] lines() {
        return lines;
    }

    @Override
    public BitSet points() {
        BitSet result = new BitSet();
        result.set(0, 13);
        return result;
    }
}
