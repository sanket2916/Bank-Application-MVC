package com.kaneki.springboot.bankapplication.entity;


import com.kaneki.springboot.bankapplication.dao.TransactionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class TransactionConverter implements Converter<String, Transaction> {

    @Autowired
    private EntityManager entityManager;

    private TransactionDao transactionDao;

    @Autowired
    public TransactionConverter(TransactionDao transactionDao, List<Transaction> transactions) {
        this.transactionDao = transactionDao;
        transactionDao.findAll().forEach(transactions::add);
    }

    @Override
    public Transaction convert(String source) {
        return transactionDao.findById(Integer.parseInt(source));
    }
}