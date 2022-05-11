package com.kaneki.springboot.bankapplication.service;

import com.kaneki.springboot.bankapplication.User.CrmUser;
import com.kaneki.springboot.bankapplication.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    public User findByUserName(String userName);
    public void save(CrmUser crmUser);
    List<User> findAllUser();
}
