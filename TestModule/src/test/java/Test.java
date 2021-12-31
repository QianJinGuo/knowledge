import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        Map<String,Integer> map = new HashMap<>();
        map.put("zhangsan",1);
        map.put("lisi",1);
        map.put("wangwu",1);
        for(String key : map.keySet()){
            System.out.println(key+":"+map.get(key));
        }
        for(Map.Entry<String,Integer> entry : map.entrySet()){
            System.out.println(entry.getKey()+":"+entry.getValue());
        }
        Set<String> keys = map.keySet();
        for (String key : keys) {
            System.out.println(key + ":" + map.get(key));
        }
        Set<Map.Entry<String, Integer>> entry = map.entrySet();
        Iterator<Map.Entry<String,Integer>> its = entry.iterator();
        while (its.hasNext()){
            Entry<String, Integer> entry1 = its.next();
            System.out.println(entry1.getKey()+":"+entry1.getValue());
        }
    }
}
