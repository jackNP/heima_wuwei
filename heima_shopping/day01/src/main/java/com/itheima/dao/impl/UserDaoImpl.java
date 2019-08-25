package com.itheima.dao.impl;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    //数据库交互的工具类
   private QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
    @Override
    public void register(User u) throws SQLException {
        /***
         * 实现用户注册
         * @param user 传入注册实体对象
         */
        String sql = "insert into user value (?,?,?,?,?,?,?,?,?,?)";
        //user对象 数据，封装进数组，相当于给所有？赋值
        Object[] params = {
                u.getUid(),u.getUsername(),u.getPassword(),
                u.getName(),u.getEmail(),u.getBirthday(),
                u.getGender(),u.getState(),u.getCode(),u.getRemark()
        };

            qr.update(sql,params);


    }

    /***
     * 用户登录的方法
     * @param username
     * @param pwd
     * @return
     */
    @Override
    public User login(String username, String pwd) throws SQLException {
        String sql = "select * from user where username = ? and password = ? ";

        User user= qr.query(sql,new BeanHandler<User>(User.class),username,pwd);

        return user;
    }
}
