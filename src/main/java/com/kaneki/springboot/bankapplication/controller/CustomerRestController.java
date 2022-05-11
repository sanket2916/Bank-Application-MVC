package com.kaneki.springboot.bankapplication.controller;

import com.kaneki.springboot.bankapplication.entity.Customer;
import com.kaneki.springboot.bankapplication.entity.Transaction;
import com.kaneki.springboot.bankapplication.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerRestController {
    @Autowired
    private CustomerService customerService;

    public CustomerRestController() {
    }

    @GetMapping("/customers")
    public List<Customer> findAll() {
        return customerService.findAll();
    }

    @GetMapping("/customers/{customerId}")
    public Customer findById(@PathVariable int customerId) {
        Customer customer = customerService.findById(customerId);
        if(customer == null) {
            throw new RuntimeException("Customer Id not found: " + customerId);
        }
        return customer;
    }

    @GetMapping("customers/transactions/{customerId}")
    public List<Transaction> showTransaction(@PathVariable int customerId) {
        Customer customer = customerService.findById(customerId);
        if(customer == null) {
            throw new RuntimeException("Customer Id not found " + customerId);
        }
        return customerService.showTransaction(customerId);
    }

    @PostMapping("/customers")
    public Customer save(@RequestBody Customer customer) {
        customer.setId(0);
        customerService.saveCustomer(customer);
        return customer;
    }

    @PostMapping("customers/transactions/{customerId}")
    public Transaction addTransaction(@PathVariable int customerId,
                                            @RequestBody Transaction transaction) {
        Customer customer = customerService.findById(customerId);
        if (customer == null) {
            throw new RuntimeException("Customer id not found " + customerId);
        }
        transaction.setId(0);
        customerService.addTransactions(customerId,transaction);
        return transaction;
    }

    @PutMapping("/customers")
    public Customer updateCustomer(@RequestBody Customer customer) {
        customerService.saveCustomer(customer);
        return customer;
    }

    @DeleteMapping("/customers/{customerId}")
    public String deleteCustomer(@PathVariable int customerId) {
        Customer customer = customerService.findById(customerId);
        if(customer == null) {
            throw new RuntimeException("Customer id not found - " + customerId);
        }
        customerService.deleteById(customerId);
        return "Deleted Cutsomer id: " + customerId;
    }
}
