package ua.ihromant;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class Client {
    private static final int SAMPLE_SIZE = 100_000;
    public static void main(String[] args) {
        String[] strings = new String[SAMPLE_SIZE];
        generateSample(strings, () -> UUID.randomUUID().toString());
        int l = 0;
        for (String s : strings) {
            l -= s.toUpperCase().hashCode();
        }
        System.out.println("Warmup: " + l);
        generateSample(strings, () -> UUID.randomUUID().toString());
        long time = System.currentTimeMillis();
        for (String s : strings) {
            l += s.toUpperCase().hashCode();
        }
        System.out.println("UUID strings: " + (System.currentTimeMillis() - time) + ", sink: " + l);
        generateSample(strings, () -> UUID.randomUUID().toString().substring(0, ThreadLocalRandom.current().nextInt(1, 5)));
        time = System.currentTimeMillis();
        for (String s : strings) {
            l += s.toUpperCase().hashCode();
        }
        System.out.println("Short strings: " + (System.currentTimeMillis() - time) + ", sink: " + l);
        generateSample(strings, Client::generateLongString);
        time = System.currentTimeMillis();
        for (String s : strings) {
            l += s.toUpperCase().hashCode();
        }
        System.out.println("Long strings: " + (System.currentTimeMillis() - time) + ", sink: " + l);
        generateSample(strings, Client::generateMixed);
        time = System.currentTimeMillis();
        for (String s : strings) {
            l += s.toUpperCase().hashCode();
        }
        System.out.println("Mixed strings: " + (System.currentTimeMillis() - time) + ", sink: " + l);
    }

    private static void generateSample(String[] array, Supplier<String> generator) {
        for (int i = 0; i < array.length; i++) {
            array[i] = generator.get();
        }
    }

    private static String generateLongString() {
        StringBuilder builder = new StringBuilder();
        int size = ThreadLocalRandom.current().nextInt(90, 100);
        for (int i = 0; i < size; i++) {
            builder.append(UUID.randomUUID());
        }
        return builder.toString();
    }

    private static String generateMixed() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            builder.append(UUID.randomUUID());
        }
        return builder.substring(0, ThreadLocalRandom.current().nextInt(1, 3500));
    }

//    private static HTMLElement computePolygon(Field field, Point center) {
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < 5; i++) {
//            builder.append(center.add(shift(i)).toString());
//            builder.append(' ');
//        }
//        builder.append(center.add(shift(5)).toString());
//        HTMLElement poly = HTMLDocument.current().createElementNS("http://www.w3.org/2000/svg", "polygon").cast();
//        poly.setId(field.getX() + "_" + field.getY());
//        poly.setAttribute("points", builder.toString());
//        poly.setAttribute("stroke", "limegreen");
//        poly.setAttribute("fill", "none");
//        return poly;
//    }
//
//    private static Point shift(int number) {
//        double ang = Math.PI / 2 - number * Math.PI / 3;
//        return new Point(HEXAGON_RADIUS * Math.cos(ang), HEXAGON_RADIUS * Math.sin(ang));
//    }
//
//    public static Point computeCenter(Field f) {
//        int xSub = 2 * f.getX() - f.getY() % 2;
//        int ySub = 3 * f.getY();
//        double xSwap = Math.sin(Math.PI / 3) * HEXAGON_RADIUS * xSub;
//        double ySwap = Math.cos(Math.PI / 3) * HEXAGON_RADIUS * ySub;
//        return new Point(X_TOP + xSwap, Y_TOP + ySwap);
//    }
}
