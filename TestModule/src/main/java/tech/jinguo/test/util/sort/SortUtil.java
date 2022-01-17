package tech.jinguo.test.util.sort;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author jinguo
 */
public class SortUtil {
    public static void main(String[] args) {
        ArrayList<Integer> arrayList = Lists.newArrayList(1,3,-1,4,5,9,8);
        System.out.println(arrayList);
        //反转
        Collections.reverse(arrayList);
        System.out.println(arrayList);
        //升序排序
        Collections.sort(arrayList);
        System.out.println(arrayList);
        //自定义排序
        Collections.sort(arrayList, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });
        System.out.println(arrayList);

    }
}
