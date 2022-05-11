package com.kaneki.springboot.bankapplication.controller;

import com.kaneki.springboot.bankapplication.User.CrmUser;
import com.kaneki.springboot.bankapplication.entity.User;
import com.kaneki.springboot.bankapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> findAllUser() {
        return userService.findAllUser();
    }

    @PostMapping("/users")
    public CrmUser save(@RequestBody CrmUser crmUser) {
        String userName = crmUser.getUserName();
        User existing = userService.findByUserName(userName);
        if(existing != null) {
            throw new RuntimeException("User already exists: " + userName);
        }
        userService.save(crmUser);
        return crmUser;
    }
}
