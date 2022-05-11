package com.kaneki.springboot.bankapplication.dao;

import com.kaneki.springboot.bankapplication.entity.Customer;
import com.kaneki.springboot.bankapplication.entity.Transaction;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class CustomerDaoImple implements CustomerDao {

    @Autowired
    @Qualifier(value = "appEntityManager")
    private EntityManager entityManager;

    public CustomerDaoImple() {
    }

    @Override
    @Transactional
    public List<Customer> findAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Customer> query =
                currentSession.createQuery("from Customer", Customer.class);
        List<Customer> customers = query.getResultList();
        for(Customer customer: customers) {
            double balance = 0;
            for(Transaction i: customer.getTransactions()){
                balance += i.getAmount();
            }
            customer.setBalance(balance);
        }
        return customers;
    }

    @Override
    @Transactional
    public Customer findById(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Customer customer = currentSession.get(Customer.class, id);
        if (customer == null) {
            throw new RuntimeException("Customer id not found - " + id);
        }
        double balance = 0;
        for(Transaction i: customer.getTransactions()) {
            balance+=i.getAmount();
        }
        customer.setBalance(balance);
        return customer;
    }

    @Override
    @Transactional
    public List<Transaction> showTransaction(int customerId) {
        Session currentSession = entityManager.unwrap(Session.class);
        Customer customer = currentSession.get(Customer.class, customerId);
        return customer.getTransactions();
    }

    @Override
    @Transactional
    public void saveCustomer(Customer customer) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(customer);
    }

    @Override
    @Transactional
    public void addTransactions(int customerId, Transaction transaction) {
        Session currentSession = entityManager.unwrap(Session.class);
        Customer customer = currentSession.get(Customer.class, customerId);
        customer.addTransaction(transaction);
        currentSession.save(customer);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Customer customer = currentSession.get(Customer.class, id);
        currentSession.delete(customer);
    }
}
