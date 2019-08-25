package com.itheima.service.impl;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.BeanFactory;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    //传入Dao接口，通过工厂，直接生成实现类。
    private UserDao userDao = BeanFactory.newInstance(UserDao.class);
    @Override
    public void register(User u) {
        try {
            userDao.register(u);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//登录方法的业务层，并且处理dao层抛过来的异常
    @Override
    public User login(String username, String pwd) {
        User user = null;
        try {
            user = userDao.login(username,pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
