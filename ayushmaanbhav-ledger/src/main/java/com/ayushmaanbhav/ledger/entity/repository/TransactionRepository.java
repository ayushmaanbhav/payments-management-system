package com.ayushmaanbhav.ledger.entity.repository;

import com.ayushmaanbhav.commonsspring.db.dao.AbstractDao;
import com.ayushmaanbhav.ledger.entity.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;

@Repository
@Transactional(readOnly = true)
public class TransactionRepository extends AbstractDao<Transaction> {
    static final String SELECT_BY_TRANSACTION_REF_ID = "select p from Transaction p where p.transactionRefId = :transactionRefId";

    public TransactionRepository() {
        super(Transaction.class);
    }

    public boolean existsByTransactionRefId(String transactionRefId) {
        return selectByTransactionRefId(transactionRefId) != null;
    }

    public Transaction selectByTransactionRefId(String transactionRefId) {
        TypedQuery<Transaction> query = createJpqlQuery(SELECT_BY_TRANSACTION_REF_ID);
        query.setParameter("transactionRefId", transactionRefId);
        return selectSingleOrNone(query);
    }
}
