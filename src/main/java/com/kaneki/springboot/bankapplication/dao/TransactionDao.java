package com.kaneki.springboot.bankapplication.dao;

import com.kaneki.springboot.bankapplication.entity.Transaction;

import java.util.List;

public interface TransactionDao {
    Transaction findById(int id);
    List<Transaction> findAll();
}
