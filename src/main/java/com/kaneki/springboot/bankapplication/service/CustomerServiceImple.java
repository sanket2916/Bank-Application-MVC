package com.kaneki.springboot.bankapplication.service;

import com.kaneki.springboot.bankapplication.dao.CustomerDao;
import com.kaneki.springboot.bankapplication.entity.Customer;
import com.kaneki.springboot.bankapplication.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImple implements CustomerService{

    @Autowired
    private CustomerDao customerDao;

    @Transactional
    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Transactional
    @Override
    public Customer findById(int id) {
        return customerDao.findById(id);
    }

    @Transactional
    @Override
    public List<Transaction> showTransaction(int customerId) {
        return customerDao.showTransaction(customerId);
    }

    @Transactional
    @Override
    public void saveCustomer(Customer customer) {
        customerDao.saveCustomer(customer);
    }

    @Transactional
    @Override
    public void addTransactions(int customerId, Transaction transaction) {
        customerDao.addTransactions(customerId, transaction);
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        customerDao.deleteById(id);
    }
}
