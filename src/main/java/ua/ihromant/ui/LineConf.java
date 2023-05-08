package ua.ihromant.ui;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LineConf {
    public static final LineConf EMPTY = gap(0);

    private final int gap;
    private final Children children;
    private final Align align;
    private final boolean stretch;

    public static LineConf gap(int gap) {
        return new LineConf(gap, null, null, false);
    }

    public static LineConf align(Align align, int gap) {
        return new LineConf(gap, null, align, false);
    }

    public static LineConf centered() {
        return centered(0);
    }

    public static LineConf centered(int gap) {
        return new LineConf(gap, null, Align.CENTERED, false);
    }

    public static LineConf stretch() {
        return new LineConf(0, null, null, true);
    }

    public static LineConf stretch(int gap) {
        return new LineConf(gap, null, null, true);
    }

    public static LineConf maxDistance() {
        return new LineConf(0, Children.MAX_DIST, null, false);
    }

    public static LineConf splitDistance(boolean stretch) {
        return new LineConf(0, Children.SPLIT_DIST, null, stretch);
    }

    public enum Children {
        MAX_DIST, SPLIT_DIST
    }

    public enum Align {
        ALIGN_START, CENTERED, ALIGN_END
    }
}
