package com.ayushmaanbhav.gatewayProvider.entity.repository;

import com.ayushmaanbhav.commonsspring.db.dao.AbstractDao;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderConfig;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;

@Repository
@Transactional(readOnly = true)
public class GatewayProviderConfigRepository extends AbstractDao<GatewayProviderConfig> {
    static final String SELECT_BY_EXTERNAL_ID = "select p from GatewayProviderConfig p where p.externalId = :externalId";

    public GatewayProviderConfigRepository() {
        super(GatewayProviderConfig.class);
    }

    public GatewayProviderConfig selectByExternalId(String externalId) {
        TypedQuery<GatewayProviderConfig> query = createJpqlQuery(SELECT_BY_EXTERNAL_ID);
        query.setParameter("externalId", externalId);
        return selectSingleOrNone(query);
    }
}
