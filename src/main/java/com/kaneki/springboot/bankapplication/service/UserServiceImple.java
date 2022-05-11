package com.kaneki.springboot.bankapplication.service;

import com.kaneki.springboot.bankapplication.User.CrmUser;
import com.kaneki.springboot.bankapplication.dao.RoleDao;
import com.kaneki.springboot.bankapplication.dao.UserDao;
import com.kaneki.springboot.bankapplication.entity.Role;
import com.kaneki.springboot.bankapplication.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImple implements UserService{
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }

    @Override
    @Transactional
    public List<User> findAllUser() {
        return userDao.findAllUser();
    }

    @Override
    @Transactional
    public void save(CrmUser crmUser) {
        User user = new User();

        user.setUserName(crmUser.getUserName());
        user.setPassword(passwordEncoder.encode(crmUser.getPassword()));
        user.setFirstName(crmUser.getFirstName());
        user.setLastName(crmUser.getLastName());
        user.setEmail(crmUser.getEmail());

        user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE")));

        userDao.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userDao.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
