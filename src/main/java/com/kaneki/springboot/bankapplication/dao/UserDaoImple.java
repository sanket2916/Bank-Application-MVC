package com.kaneki.springboot.bankapplication.dao;

import com.kaneki.springboot.bankapplication.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserDaoImple implements UserDao{

    @Autowired
    private EntityManager entityManager;

    @Override
    public User findByUserName(String userName) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<User> query = currentSession.createQuery("from User where userName=:userName", User.class);
        query.setParameter("userName", userName);
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
            user = null;
        }
        return user;
    }

    @Override
    public void save(User user) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(user);
    }

    @Override
    public List<User> findAllUser() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<User> query = currentSession.createQuery("from User", User.class);
        List<User> users = query.getResultList();
        return users;
    }
}
