package com.kaneki.springboot.bankapplication.service;

import com.kaneki.springboot.bankapplication.User.CrmUser;
import com.kaneki.springboot.bankapplication.entity.Customer;
import com.kaneki.springboot.bankapplication.entity.Transaction;
import com.kaneki.springboot.bankapplication.entity.User;

import java.util.List;

public interface RestService {
    List<Customer> getCustomerList();
    Customer findCustomerById(int id);
    void saveCustomer(Customer customer);
    void deleteCustomerById(int id);
    List<Transaction> getCustomerTransactions(int customerId);
    void saveTransactions(int customerId, Transaction transaction);
    List<User> findAllUser();
    void saveUser(CrmUser crmUser);
}
