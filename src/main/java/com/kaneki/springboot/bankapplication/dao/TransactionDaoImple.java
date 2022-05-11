package com.kaneki.springboot.bankapplication.dao;

import com.kaneki.springboot.bankapplication.entity.Transaction;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository(value = "transactionDao")
public class TransactionDaoImple implements TransactionDao {

    @Autowired
    @Qualifier(value = "appEntityManager")
    private EntityManager entityManager;

    public TransactionDaoImple() {
    }

    @Override
    @Transactional
    public Transaction findById(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Transaction transaction = currentSession.get(Transaction.class, id);
        if (transaction == null) {
            throw new RuntimeException("Transaction id not found - " + id);
        }
        return transaction;
    }

    @Override
    @Transactional
    public List<Transaction> findAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Transaction> query =
                currentSession.createQuery("from Transaction", Transaction.class);
        List<Transaction> transactions = query.getResultList();
        return transactions;
    }
}
