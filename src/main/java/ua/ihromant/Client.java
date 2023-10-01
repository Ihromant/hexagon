package ua.ihromant;

import java.util.Arrays;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        int[] ints = new int[]{1, 2, 3, 4, 5};
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(ints.length);
        System.out.println(integers.size());
    }
}
