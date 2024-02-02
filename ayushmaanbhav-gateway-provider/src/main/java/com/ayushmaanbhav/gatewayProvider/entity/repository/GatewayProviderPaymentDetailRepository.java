package com.ayushmaanbhav.gatewayProvider.entity.repository;

import com.ayushmaanbhav.commonsspring.db.dao.AbstractDao;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderPaymentDetail;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class GatewayProviderPaymentDetailRepository extends AbstractDao<GatewayProviderPaymentDetail> {
    public GatewayProviderPaymentDetailRepository() {
        super(GatewayProviderPaymentDetail.class);
    }
}
