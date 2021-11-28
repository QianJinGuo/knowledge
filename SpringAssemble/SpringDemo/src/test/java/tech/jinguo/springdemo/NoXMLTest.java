package tech.jinguo.springdemo;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tech.jinguo.springdemo.annotation.config.SpringConfiguration;
import tech.jinguo.springdemo.annotation.service.UserService;

public class NoXMLTest {
    private final AnnotationConfigApplicationContext ioc= new AnnotationConfigApplicationContext(SpringConfiguration.class);

    @Test
    public void test(){
        UserService userService = (UserService) ioc.getBean("userService");
        userService.addUser();
    }
}
