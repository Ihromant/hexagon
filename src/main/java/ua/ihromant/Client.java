package ua.ihromant;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntSupplier;

public class Client {
    public static void main(String[] args) {
        int[] integers = new int[1_000_000];
        int sink = 0;
        fill(integers, ThreadLocalRandom.current()::nextInt);
        for (int i : integers) {
            sink += Integer.toString(i).length();
        }
        System.out.println("Warmup: " + sink);
        fill(integers, ThreadLocalRandom.current()::nextInt);
        long time = System.currentTimeMillis();
        for (int i : integers) {
            sink += Integer.toString(i).length();
        }
        System.out.println("Mixed integers: " + (System.currentTimeMillis() - time) + ", sink: " + sink);
        fill(integers, () -> ThreadLocalRandom.current().nextInt(-99, 100));
        time = System.currentTimeMillis();
        for (int i : integers) {
            sink += Integer.toString(i).length();
        }
        System.out.println("Small integers: " + (System.currentTimeMillis() - time) + ", sink: " + sink);
        fill(integers, () -> ThreadLocalRandom.current().nextBoolean()
                ? ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, -100_000_000)
                : ThreadLocalRandom.current().nextInt(100_000_000, Integer.MAX_VALUE));
        time = System.currentTimeMillis();
        for (int i : integers) {
            sink += Integer.toString(i).length();
        }
        System.out.println("Large integers: " + (System.currentTimeMillis() - time) + ", sink: " + sink);
        fill(integers, ThreadLocalRandom.current()::nextInt);
        time = System.currentTimeMillis();
        for (int i = 0; i < integers.length; i++) {
            int val = integers[i];
            sink += Integer.toString(val, i % 30 + 2).length();
        }
        System.out.println("Integers with radix: " + (System.currentTimeMillis() - time) + ", sink: " + sink);
    }

    private static void fill(int[] values, IntSupplier generator) {
        for (int i = 0; i < values.length; i++) {
            values[i] = generator.getAsInt();
        }
    }

    private static String generateTag() {
        return ThreadLocalRandom.current().nextInt(2) == 0 ? "div" : "span";
    }

    private static Point generatePoint() {
        return new Point(ThreadLocalRandom.current().nextInt(0, 800), ThreadLocalRandom.current().nextInt(0, 600));
    }

    private record Point(int x, int y) {}
}
