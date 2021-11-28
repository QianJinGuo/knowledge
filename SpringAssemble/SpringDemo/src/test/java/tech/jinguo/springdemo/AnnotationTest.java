package tech.jinguo.springdemo;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tech.jinguo.springdemo.annotation.beans.User;
import tech.jinguo.springdemo.annotation.dao.UserDao;
import tech.jinguo.springdemo.annotation.service.UserService;

public class AnnotationTest {
    //创建IoC容器
    private ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext(
            "classpath:beans-annotation.xml");

    @Test
    public void testUser(){
        User user = (User) ioc.getBean("user");
        System.out.println(user);
    }

    @Test
    public void testUserDao(){
        UserDao userDao = (UserDao) ioc.getBean("userDao");
        System.out.println(userDao);
    }

    @Test
    public void testUserService(){
        //Spring帮我们创建的UserServiceImpl对象里面确实注入UserDaoImpl,但是我们自己new的UserServiceImpl需要我们自己设置UserDaoImpl
        //UserService userService = new UserServiceImpl();

        UserService userService = (UserService) ioc.getBean("userService");
        System.out.println(userService);
        userService.addUser();
    }
}
