package tech.jinguo.springdemo.async;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

@ContextConfiguration(classes = SpringTestConfiguration.class)
@RunWith(SpringRunner.class)
public class CommonAsyncJobs {
    @Autowired
    private TestAsyncClient testAsyncClient;

    // 多线程环境下，普通的list.add不适用，用Vector处理就行了，效率低，但无所谓反正测试的
    public static Vector<String> list = new Vector<>();

    @Test
    public void testAsync() throws Exception {
        // 循环里面异步处理
        int a = 10000;
        for (int i = 0; i < a; i++) {
            testAsyncClient.test(i);
        }
        System.out.println("多线程结果：" + list.size());
        Set<String> set = new HashSet<>();
        Set<String> exist = new HashSet<>();
        //如果使用.增强for循环和iterator遍历，会出现checkForComodification异常
        //如果要使用for或者iterator，则需要使用可重入锁ReentrantLock
        for (int i = 0; i < list.size(); i++) {
            if (set.contains(list.get(i))) {
                exist.add(list.get(i));
            } else {
                set.add(list.get(i));
            }
        }
        // 没重复的值，说明多线程环境下，全局变量没有问题
        System.out.println("重复的值：" + exist.size());
        System.out.println("重复的值：" + exist);
        // 单元测试内主线程结束会终止子线程任务
        Thread.sleep(Long.MAX_VALUE);
    }
}

