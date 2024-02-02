package com.ayushmaanbhav.ledger.entity.repository;

import com.ayushmaanbhav.commonsspring.db.dao.AbstractDao;
import com.ayushmaanbhav.ledger.entity.Account;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;

@Repository
@Transactional(readOnly = true)
public class AccountRepository extends AbstractDao<Account> {
    static final String SELECT_BY_EXTERNAL_ID = "select p from Account p where p.externalId = :externalId";
    static final String SELECT_BY_REQUEST_ID = "select p from Account p where p.requestId = :requestId";

    public AccountRepository() {
        super(Account.class);
    }

    public Account selectByExternalId(String externalId) {
        TypedQuery<Account> query = createJpqlQuery(SELECT_BY_EXTERNAL_ID);
        query.setParameter("externalId", externalId);
        return selectSingleOrNone(query);
    }

    public boolean existsByRequestId(String requestId) {
        return selectByRequestId(requestId) != null;
    }

    public Account selectByRequestId(String requestId) {
        TypedQuery<Account> query = createJpqlQuery(SELECT_BY_REQUEST_ID);
        query.setParameter("requestId", requestId);
        return selectSingleOrNone(query);
    }
}
