package com.itheima.dao;

import com.itheima.domain.User;

import java.sql.SQLException;

public interface UserDao {
    void register(User user) throws SQLException;
    User login(String username, String pwd) throws SQLException;
}
