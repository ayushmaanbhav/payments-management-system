package com.ayushmaanbhav.requestIdempotency.entity.repository;

import com.ayushmaanbhav.commonsspring.db.dao.AbstractDao;
import com.ayushmaanbhav.requestIdempotency.entity.RequestIdempotencyDetail;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;

@Repository
@Transactional(readOnly = true)
public class RequestIdempotencyDetailRepository extends AbstractDao<RequestIdempotencyDetail> {
    static final String SELECT_BY_EXTERNAL_ID = "select p from RequestIdempotencyDetail p where p.externalId = :externalId";

    public RequestIdempotencyDetailRepository() {
        super(RequestIdempotencyDetail.class);
    }

    public RequestIdempotencyDetail selectByExternalId(String externalId) {
        TypedQuery<RequestIdempotencyDetail> query = createJpqlQuery(SELECT_BY_EXTERNAL_ID);
        query.setParameter("externalId", externalId);
        return selectSingleOrNone(query);
    }
}
