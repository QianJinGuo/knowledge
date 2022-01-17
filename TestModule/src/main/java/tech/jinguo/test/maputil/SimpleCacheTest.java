package tech.jinguo.test.maputil;

import java.util.stream.IntStream;

/**
 * @author jinguo
 */
public class SimpleCacheTest {
    public static void main(String[] args) {
        SimpleCache<Integer,Integer> cache = new SimpleCache<>(3);
        IntStream.range(0,9).forEach(i->cache.save(i,i*i));
        System.out.println("插入10个键值对后，缓存内容：");
        System.out.println(cache + "\n");

        System.out.println("访问键值为7的节点后，缓存内容：");
        cache.getOne(6);
        System.out.println(cache + "\n");

        System.out.println("插入键值为1的键值对后，缓存内容：");
        cache.save(1, 1);
        System.out.println(cache);
    }
}
