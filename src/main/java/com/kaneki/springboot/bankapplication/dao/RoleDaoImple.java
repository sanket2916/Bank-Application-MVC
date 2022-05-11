package com.kaneki.springboot.bankapplication.dao;

import com.kaneki.springboot.bankapplication.entity.Role;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class RoleDaoImple implements RoleDao{

    @Autowired
    private EntityManager entityManager;

    @Override
    public Role findRoleByName(String roleName) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Role> query = currentSession.createQuery("from Role where name=:roleName", Role.class);
        query.setParameter("roleName", roleName);
        Role role = null;
        try {
            role = query.getSingleResult();
        } catch (Exception e) {
            role = null;
        }
        return role;
    }
}
