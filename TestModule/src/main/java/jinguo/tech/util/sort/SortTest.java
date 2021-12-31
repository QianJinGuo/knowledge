package jinguo.tech.util.sort;

import java.util.Set;
import java.util.TreeMap;

/**
 * @author jinguo
 */
public class SortTest {
    public static void main(String[] args) {
        //存放到map中的对象要重写equals和hashcode
        TreeMap<Person,String> pMap = new TreeMap<>();
        pMap.put(new Person("zhangsan",20),"suzhou");
        pMap.put(new Person("lisi",25),"shanghai");
        pMap.put(new Person("wangwu",30),"hangzhou");
        Set<Person> p = pMap.keySet();
        p.forEach(System.out::println);
    }
}
