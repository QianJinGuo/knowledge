package tech.jinguo.webflux.test;

import java.util.stream.IntStream;

public class Test {
    public static void main(String[] args) {
        int[] arr = {22, 441, 55, 131, 455};
        int min = Integer.MAX_VALUE;
        for (int i : arr) {
            if (i < min) {
                min = i;
            }
        }
        System.out.println(min);

        System.out.println(IntStream.of(arr).parallel().min().getAsInt());
    }

}
