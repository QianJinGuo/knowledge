package tech.jinguo.springdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tech.jinguo.springdemo.annotation.beans.User;
import tech.jinguo.springdemo.annotation.config.SpringConfiguration;
import tech.jinguo.springdemo.annotation.service.UserService;

import javax.annotation.Resource;
//@ContextConfiguration(locations = "classpath:beans-annotation.xml")
@ContextConfiguration(classes = SpringConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringJunitTest {
    @Resource(name = "userService")
    private UserService userService;
    @Resource(name = "user2")
    private User user2;
    @Test
    public void testService() {
        userService.addUser();
    }

    @Test
    public void testUser(){
        System.out.println(user2);
        System.out.println(user2);
    }
}
