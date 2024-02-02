package com.ayushmaanbhav.gatewayProvider.entity.repository;

import com.ayushmaanbhav.commonsspring.db.dao.AbstractDao;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderEvent;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class GatewayProviderEventRepository extends AbstractDao<GatewayProviderEvent> {
    public GatewayProviderEventRepository() {
        super(GatewayProviderEvent.class);
    }
}
