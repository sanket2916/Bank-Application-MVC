package com.kaneki.springboot.bankapplication.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    @NotNull(message = "First Name cannot be empty")
    @Size(min = 1, message = "First Name cannot be empty")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "Last Name cannot be empty")
    @Size(min = 1, message = "Last Name cannot be empty")
    private String lastName;

    @Column(name = "email")
    @NotNull(message = "Email ID cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9.+_-]+@[a-zA-Z0-9.-]+$",
            message = "Invalid Email")
    private String email;

    @Column(name = "branch")
    @NotNull(message = "Branch name cannot be empty")
    @Size(min = 1, message = "Branch name cannot be empty")
    private String branch;

    @Column(name = "balance")
    private double balance;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private List<Transaction> transactions;

    public Customer(String firstName, String lastName, String email, String branch, double balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.branch = branch;
        this.balance = balance;
    }

    public Customer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction){
        if(transaction == null) {
            transactions = new ArrayList<>();
        } else if(balance + transaction.getAmount()>=0) {
            transactions.add(transaction);
        } else if(balance + transaction.getAmount()<0) {
            throw new RuntimeException("Not sufficient funds");
        }
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", branch='" + branch + '\'' +
                ", balance=" + balance +
                '}';
    }
}
