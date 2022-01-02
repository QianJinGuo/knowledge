package jinguo.tech.util.collections;

import java.util.ArrayList;
import java.util.List;

public class EnsureCapacityTest {
    public static void main(String[] args) {
        commonAdd();
        //最好在 add 大量元素之前用 ensureCapacity 方法，以减少增量重新分配的次数
        //实际上初始化list的时候，指定容量是最快的
        useEnsureCapacityBeforeAdd();
    }

    public static void commonAdd() {
        List<Object> list = new ArrayList<>();
        final int N = 10000000;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            list.add(i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("使用ensureCapacity方法前：" + (endTime - startTime));
    }

    public static void useEnsureCapacityBeforeAdd() {
        ArrayList<Object> list;
        final int N = 10000000;
        list = new ArrayList<>();
        long startTime1 = System.currentTimeMillis();
        list.ensureCapacity(N);
        for (int i = 0; i < N; i++) {
            list.add(i);
        }
        long endTime1 = System.currentTimeMillis();
        System.out.println("使用ensureCapacity方法后：" + (endTime1 - startTime1));
    }
}
