package tech.jinguo.test.maputil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author jinguo
 * LinkedHashMap用做LRU缓存
 */
public class SimpleCache<K,V> extends LinkedHashMap<K,V> {
    private static final int MAX_NODE_NUM = 100;
    private final int limit;

    public SimpleCache(){
        this(MAX_NODE_NUM);
    }

    public SimpleCache(int limit){
        super(limit,0.75f,true);
        this.limit = limit;
    }

    public V save(K key,V val){
        return put(key,val);
    }

    public V getOne(K key) {
        return get(key);
    }

    public boolean exists(K key) {
        return containsKey(key);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size()>limit;
    }
}
