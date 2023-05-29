package ua.ihromant.domain;

public record Edge(int from, int to) {
    public static Edge from(int from, int to) {
        if (from == to) {
            throw new IllegalArgumentException();
        }
        return new Edge(Math.min(from, to), Math.max(from, to));
    }
}
