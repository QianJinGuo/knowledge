package tech.jinguo.springdemo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationTest {
    //创建IoC容器
    private ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("classpath:beans-annotation.xml");
}
