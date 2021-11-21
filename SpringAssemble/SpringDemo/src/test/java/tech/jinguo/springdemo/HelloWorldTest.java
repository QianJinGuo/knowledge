package tech.jinguo.springdemo;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tech.jinguo.springdemo.bean.HelloWorld;

public class HelloWorldTest {
    @Test
    public void testHelloWorld() {
        //创建IoC容器对象
        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
        //从IoC容器中获取HelloWorld对象
        //方式一 根据Bean的id获取
        //HelloWorld helloWorld = (HelloWorld) ioc.getBean("helloWorld");
        //方式二 根据Bean的类型获取，如果有多个该类型的bean会抛异常
        //HelloWorld helloWorld = ioc.getBean(HelloWorld.class);
        //方式三 根据Bean的id和类型获取
        HelloWorld helloWorld = ioc.getBean("helloWorld2", HelloWorld.class);
        //调用对象中的方法
        helloWorld.test();
    }
}
