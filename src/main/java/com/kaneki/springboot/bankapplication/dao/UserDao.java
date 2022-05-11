package com.kaneki.springboot.bankapplication.dao;

import com.kaneki.springboot.bankapplication.entity.User;

import java.util.List;

public interface UserDao {
    User findByUserName(String userName);
    void save(User user);
    List<User> findAllUser();
}
