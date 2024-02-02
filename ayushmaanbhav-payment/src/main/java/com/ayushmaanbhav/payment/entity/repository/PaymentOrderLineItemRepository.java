package com.ayushmaanbhav.payment.entity.repository;

import com.ayushmaanbhav.commonsspring.db.dao.AbstractDao;
import com.ayushmaanbhav.payment.entity.PaymentOrderLineItem;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;

@Repository
@Transactional(readOnly = true)
public class PaymentOrderLineItemRepository extends AbstractDao<PaymentOrderLineItem> {
    static final String SELECT_BY_EXTERNAL_ID = "select p from PaymentOrderLineItem p where p.externalId = :externalId";

    public PaymentOrderLineItemRepository() {
        super(PaymentOrderLineItem.class);
    }

    public PaymentOrderLineItem selectByExternalId(String externalId) {
        TypedQuery<PaymentOrderLineItem> query = createJpqlQuery(SELECT_BY_EXTERNAL_ID);
        query.setParameter("externalId", externalId);
        return selectSingleOrNone(query);
    }
}
