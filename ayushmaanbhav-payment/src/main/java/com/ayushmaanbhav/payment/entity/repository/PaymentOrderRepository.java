package com.ayushmaanbhav.payment.entity.repository;

import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.commonsspring.db.dao.AbstractDao;
import com.ayushmaanbhav.payment.entity.PaymentOrder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class PaymentOrderRepository extends AbstractDao<PaymentOrder> {
    static final String SELECT_BY_EXTERNAL_ID = "select p from PaymentOrder p where p.externalId = :externalId";
    static final String SELECT_BY_STATUS = "select p from PaymentOrder p where p.status = :status";

    public PaymentOrderRepository() {
        super(PaymentOrder.class);
    }

    public PaymentOrder selectByExternalId(String externalId) {
        TypedQuery<PaymentOrder> query = createJpqlQuery(SELECT_BY_EXTERNAL_ID);
        query.setParameter("externalId", externalId);
        return selectSingleOrNone(query);
    }

    public List<PaymentOrder> selectByStatus(PaymentStatus status) {
        TypedQuery<PaymentOrder> query = createJpqlQuery(SELECT_BY_STATUS);
        query.setParameter("status", status);
        return query.getResultList();
    }
}
