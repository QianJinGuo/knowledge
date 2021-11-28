package tech.jinguo.springdemo.annotation.dao.impl;

import org.springframework.stereotype.Repository;
import tech.jinguo.springdemo.annotation.dao.UserDao;

//默认情况下Spring的对象在IOC容器中的id是类名的首字母小写
//我们可以通过注解的value属性值来指定该id，而且该value属性值可以省略
//@Repository(value = "userDao")
@Repository("userDao2")
public class UserDaoImpl2 implements UserDao {
    @Override
    public void addUser() {
        System.out.println("插入用户信息");
    }
}
