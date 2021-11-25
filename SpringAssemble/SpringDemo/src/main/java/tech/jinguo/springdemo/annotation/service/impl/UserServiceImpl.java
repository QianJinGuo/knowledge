package tech.jinguo.springdemo.annotation.service.impl;

import org.springframework.stereotype.Service;
import tech.jinguo.springdemo.annotation.dao.UserDao;
import tech.jinguo.springdemo.annotation.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;


    @Override
    public void addUser() {
        //调用userDao中添加用户信息的方法
    }
}
