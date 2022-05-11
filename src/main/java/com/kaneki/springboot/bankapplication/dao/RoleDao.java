package com.kaneki.springboot.bankapplication.dao;

import com.kaneki.springboot.bankapplication.entity.Role;

public interface RoleDao {
    Role findRoleByName(String roleName);
}
