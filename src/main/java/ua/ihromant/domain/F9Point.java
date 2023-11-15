package ua.ihromant.domain;

import java.util.Arrays;
import java.util.BitSet;
import java.util.stream.Stream;

public record F9Point(F9 x, F9 y) {
    public F9Point add(F9Point that) {
        return new F9Point(this.x.add(that.x), this.y.add(that.y));
    }

    public F9Point sub(F9Point that) {
        return new F9Point(this.x.sub(that.x), this.y.sub(that.y));
    }

    public F9Point mul(F9 scalar) {
        return new F9Point(scalar.mul(x), scalar.mul(y));
    }

    private static BitSet[] generateLines() {
        int cardinality = F9.COUNT;
        int v = cardinality * cardinality + cardinality + 1;
        F9Point[][] rays = Stream.concat(
                Stream.<F9Point[]>of(Arrays.stream(F9.values).filter(val -> !val.equals(F9.ZERO))
                        .map(y -> new F9Point(F9.ZERO, y)).toArray(F9Point[]::new)),
                Arrays.stream(F9.values).map(y -> Arrays.stream(F9.values).filter(val -> !val.equals(F9.ZERO))
                        .map(cf -> new F9Point(F9.ONE, y).mul(cf)).toArray(F9Point[]::new))).toArray(F9Point[][]::new);
        BitSet[] lines = new BitSet[v];
        for (int i = 0; i < cardinality; i++) {
            F9Point start = new F9Point(F9.ZERO, F9.values[i]);
            for (int j = 0; j < cardinality; j++) {
                int lineIdx = i * cardinality + j;
                BitSet line = new BitSet();
                line.set(start.idx());
                Arrays.stream(rays[j + 1]).forEach(p -> line.set(start.add(p).idx()));
                line.set(cardinality * cardinality + j);
                lines[lineIdx] = line;
            }
        }
        for (int i = 0; i < cardinality; i++) {
            int lineIdx = cardinality * cardinality + i;
            BitSet line = new BitSet();
            F9Point start = new F9Point(F9.values[i], F9.ZERO);
            line.set(start.idx());
            Arrays.stream(rays[0]).forEach(p -> line.set(start.add(p).idx()));
            line.set(cardinality * cardinality + cardinality);
            lines[lineIdx] = line;
        }
        BitSet infinity = new BitSet();
        for (int i = 0; i <= cardinality; i++) {
            infinity.set(cardinality * cardinality + i);
        }
        lines[cardinality * cardinality + cardinality] = infinity;
        return lines;
    }

    public static BitSet[] generateUnital() {
        int q = 3;
        int ord = F9.COUNT;
        int v = ord * ord + ord + 1;
        BitSet[] lines = generateLines();
        BitSet unital = new BitSet();
        for (int p = 0; p < v; p++) {
            int[] hom = getHomogeneus(p, ord);
            F9 val = Arrays.stream(hom).mapToObj(crd -> F9.values[crd].exponent(q + 1)).reduce(F9.ZERO, F9::add);
            if (F9.ZERO.equals(val)) {
                unital.set(p);
            }
        }
        return Arrays.stream(lines).map(l -> l.stream().filter(unital::get)
                        .collect(BitSet::new, BitSet::set, BitSet::or))
                .filter(bs -> bs.cardinality() > 1).toArray(BitSet[]::new);
    }

    private static int[] getHomogeneus(int p, int ord) {
        int sqr = ord * ord;
        if (p < sqr) {
            return new int[]{p / ord, p % ord, F9.ONE.idx()};
        }
        p = p - sqr;
        if (p < ord) {
            return new int[]{F9.ONE.idx(), p, F9.ZERO.idx()};
        } else {
            return new int[]{F9.ONE.idx(), F9.ZERO.idx(), F9.ZERO.idx()};
        }
    }

    public int idx() {
        return x.idx() * F9.COUNT + y.idx();
    }

    public F9Point fromIdx(int idx) {
        return new F9Point(F9.values[idx / F9.COUNT], F9.values[idx % F9.COUNT]);
    }
}
