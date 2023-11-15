package ua.ihromant.domain;

import java.util.Arrays;
import java.util.stream.IntStream;
public record F9(int re, int im) implements Comparable<F9> {
    public static final F9[] values = IntStream.range(0, 9).mapToObj(i -> new F9((i / 3) - 1, (i % 3) - 1)).toArray(F9[]::new);
    public static final int COUNT = values.length;
    public static final F9 ZERO = new F9(0, 0);
    public static final F9 ONE = new F9(1, 0);
    public static final F9 MINUS_ONE = new F9(-1, 0);

    public static final F9[] ONE_ROOTS = Arrays.stream(values).filter(v -> ONE.equals(v.exponent(4))).toArray(F9[]::new);
    public static final F9[] MINUS_ONE_ROOTS = Arrays.stream(values).filter(v -> MINUS_ONE.equals(v.exponent(4))).toArray(F9[]::new);

    @Override
    public int compareTo(F9 that) {
        int dRe = this.re - that.re;
        if (dRe != 0) {
            return dRe;
        }
        return this.im - that.im;
    }

    public int idx() {
        return (re + 1) * 3 + (im + 1);
    }

    public F9 add(F9 that) {
        return new F9(modAdd(this.re, that.re), modAdd(this.im, that.im));
    }

    public F9 sub(F9 that) {
        return new F9(modAdd(this.re, -that.re), modAdd(this.im, -that.im));
    }

    public F9 mul(F9 that) {
        return new F9(modAdd(this.re * that.re, -this.im * that.im), modAdd(this.re * that.im, this.im * that.re));
    }

    public F9 exponent(int power) {
        F9 result = new F9(1, 0);
        F9 base = this;
        while (power > 0) {
            if (power % 2 == 1) {
                result = result.mul(base);
            }
            base = base.mul(base);
            power = power / 2;
        }
        return result;
    }

    private static int modAdd(int a, int b) {
        int res = a + b;
        if (res == 2) {
            res = -1;
        }
        if (res == -2) {
            res = 1;
        }
        return res;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (re != 0) {
            result.append(re);
        }
        if (im == 1 && !result.isEmpty()) {
            result.append('+');
        }
        if (im != 0) {
            result.append(im);
        }
        if (result.isEmpty()) {
            return "0";
        }
        return result.toString();
    }
}
