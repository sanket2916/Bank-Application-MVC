package com.kaneki.springboot.bankapplication.service;

import com.kaneki.springboot.bankapplication.entity.Customer;
import com.kaneki.springboot.bankapplication.entity.Transaction;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();
    Customer findById(int id);
    List<Transaction> showTransaction(int customerId);
    void saveCustomer(Customer customer);
    void addTransactions(int customerId, Transaction transaction);
    void deleteById(int id);
}
