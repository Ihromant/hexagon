package ua.ihromant.domain;

import java.util.BitSet;

public record LineData(BitSet points, TextColor color, Point[][] bezier) {
}
