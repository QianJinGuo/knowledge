package tech.jinguo.springdemo.annotation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.jinguo.springdemo.annotation.dao.UserDao;
import tech.jinguo.springdemo.annotation.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
    /**
     * 基于注解的方式自动装配（注入）的过程
     * 1.按照属性的类型自动装配
     * 2.如果按照类型无法自动装配，则以属性名作为id从IOC容器中寻找以实现自动装配
     * 3.还可以通过@Qualifer注解指定bean的名称实现自动装配。@Qualifer注解也可以标注在方法的形参前面
     */
    //@Qualifier("userDao1")
    //添加了@Autowired 注解的属性默认必须注入成功，否则会抛出异常，我们可以通过该注解的required=false来告诉Spring当前属性不是必须的
    @Autowired
    private UserDao userDao1;

    @Override
    public void addUser() {
        //调用userDao中添加用户信息的方法
        userDao1.addUser();
    }
}
