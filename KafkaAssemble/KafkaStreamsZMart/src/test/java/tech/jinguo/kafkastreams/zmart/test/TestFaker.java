/*
 *
 * @Project: knowledge
 * @File: TestFaker
 * @Package: tech.jinguo.kafkastreams.zmart.test
 * @Date: 2021/10/18 15:27
 * @Author: by jinguo.qian
 *
 */
package tech.jinguo.kafkastreams.zmart.test;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tech.jinguo.kafkastreams.zmart.util.datagen.DataGenerator;

/**
 * @author jinguo.qian
 * @title: TestFaker
 * @projectName knowledge
 * @description: 测试生成faker数据
 * @date 2021/10/18 15:27
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestFaker {
    @Test
    public void testFaker(){
        //DataGenerator.generateRandomText().forEach(log::info);
        DataGenerator.generateCustomers(100).forEach(customer -> System.out.println(JSON.toJSONString(customer)));
    }
}
    