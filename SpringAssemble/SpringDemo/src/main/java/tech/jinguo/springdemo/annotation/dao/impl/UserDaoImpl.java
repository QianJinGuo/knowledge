package tech.jinguo.springdemo.annotation.dao.impl;

import org.springframework.stereotype.Repository;
import tech.jinguo.springdemo.annotation.dao.UserDao;
@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public void addUser() {
        System.out.println("插入用户信息");
    }
}
