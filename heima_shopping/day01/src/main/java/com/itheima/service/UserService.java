package com.itheima.service;

import com.itheima.domain.User;

public interface UserService {
    void register(User u);
    User login(String username, String pwd);
}

