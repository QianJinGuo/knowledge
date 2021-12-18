package tech.jinguo.springdemo.spring5;


import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(locations = "classpath:beans-spring5.xml")
public class SpringTest {
    @Test
    public void test(){
        System.out.println("测试打印的日志");
    }
}
