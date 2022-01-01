package jinguo.tech.util.collections;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionsTest {
    public static void main(String[] args) {
        arrayToListTest();
    }
    //List To Map
    public static void listToMapTest(){
        List<Person> bookList = new ArrayList<>();
        bookList.add(new Person("zhangsan","123456789"));
        bookList.add(new Person("lisi",null));
        // 空指针异常
        bookList.stream().collect(Collectors.toMap(Person::getName, Person::getPhone));
    }

    //删除元素
    public static void removeElement(){
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 10; ++i) {
            list.add(i);
        }
        list.removeIf(filter -> filter % 2 == 0); /* 删除list中的所有偶数 */
        System.out.println(list); /* [1, 3, 5, 7, 9] */
    }

    public static void toArrayTest(){
        String [] s= new String[]{
                "dog", "lazy", "a", "over", "jumps", "fox", "brown", "quick", "A"
        };
        List<String> list = Arrays.asList(s);
        //不能使用其修改集合相关的方法,否则会抛出UnsupportedOperationException
        //list.add("cat");
        System.out.println(list.getClass());
        System.out.println(new ArrayList<>(list).getClass());
        Collections.reverse(list);
        //没有指定类型的话会报错
        s=list.toArray(new String[0]);
        for (String s1 : s) {
            System.out.print(s1+" ");
        }

        int[] myArray = {1, 2, 3};
        //传递的数组必须是对象数组，而不是基本类型。
        //用包装类型可以解决问题
        List list1 = Arrays.asList(myArray);
        System.out.println(list1.get(0));   //数组地址值
        //System.out.println(list1.get(1));   //报错：ArrayIndexOutOfBoundsException
        int[] array = (int[]) list1.get(0);
        System.out.println(array[0]);//1
    }

    public static void arrayToListTest(){
        List list = new ArrayList<>(Arrays.asList("a", "b", "c"));
        System.out.println(list.getClass());

        Integer [] myArray = { 1, 2, 3 };
        List myList = Arrays.stream(myArray).collect(Collectors.toList());
        System.out.println(myList.getClass());
        //基本类型也可以实现转换（依赖boxed的装箱操作）
        int [] myArray2 = { 1, 2, 3 };
        List myList2 = Arrays.stream(myArray2).boxed().collect(Collectors.toList());
        System.out.println(myList2.getClass());

        List<String> il = ImmutableList.of("string", "elements");  // from varargs
        List<String> il2 = ImmutableList.copyOf(myList2);      // from array
        System.out.println(il);
        System.out.println(il2);

        List<String> l1 = Lists.newArrayList(il);    // from collection
        List<String> l2= Lists.newArrayList("or", "string", "elements"); // from varargs
        System.out.println(l1);
        System.out.println(l2);

        List<String> l = new ArrayList<String>();
        CollectionUtils.addAll(list, "123");
    }
}

@Setter
@Getter
@AllArgsConstructor
class Person{
    private String name;
    private String phone;
}
