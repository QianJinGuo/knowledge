package tech.jinguo.springdemo.aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tech.jinguo.springdemo.aop.beans.Calculator;

public class AOPTest {
    //创建IOC容器
    ApplicationContext ioc = new ClassPathXmlApplicationContext("classpath:beans-aop.xml");

    @Test
    public void testAop() {
        Calculator calcuator = (Calculator) ioc.getBean("calculator");
        int add = calcuator.add(10,2);
        System.out.println(add);

        int sub = calcuator.sub(10,2);
        System.out.println(sub);

        int mul = calcuator.mul(10,2);
        System.out.println(mul);

        int div =calcuator.div(10,0);
        System.out.println(div);
    }
}
