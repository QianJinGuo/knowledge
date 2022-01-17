package tech.jinguo.test.util;

import java.util.stream.IntStream;

public class BinarySearchUtil {
    // jdk中Arrays的二分查找源码
    // Like public version, but without range checks.
    private static int binarySearch0(int[] a, int fromIndex, int toIndex,
                                     int key) {
        int low = fromIndex;
        int high = toIndex - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            int midVal = a[mid];

            if (midVal < key) {
                low = mid + 1;
            } else if (midVal > key) {
                high = mid - 1;
            } else {
                return mid; // key found
            }
        }
        return -(low + 1);  // key not found.
    }

    public static void main(String[] args) {
        int[] arr = IntStream.range(0,9).toArray();
        System.out.println(binarySearch0(arr,0,arr.length,100));
    }
}
