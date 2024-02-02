package com.ayushmaanbhav.gatewayProvider.entity.repository;

import com.ayushmaanbhav.commonsspring.db.dao.AbstractDao;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayConnectionSetting;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GatewayConnectionSettingRepository extends AbstractDao<GatewayConnectionSetting> {

    static String SELECT_BY_EXTERNAL_ID = "select g from GatewayConnectionSetting g " +
            "where g.externalId = :externalId";
    static String SELECT_BY_REFRESH_ENABLED = "select g from GatewayConnectionSetting g " +
            "where g.tokenRefreshEnabled = :tokenRefreshEnabled";
    public GatewayConnectionSettingRepository() {
        super(GatewayConnectionSetting.class);
    }

    @Transactional(readOnly = true)
    public GatewayConnectionSetting selectByExternalId(String externalId) {
        TypedQuery<GatewayConnectionSetting> query = createJpqlQuery(SELECT_BY_EXTERNAL_ID);
        query.setParameter("externalId", externalId);
        return selectSingleOrNone(query);
    }

    @Transactional(readOnly = true)
    public List<GatewayConnectionSetting> selectByRefreshEnabled(boolean tokenRefreshEnabled) {
        TypedQuery<GatewayConnectionSetting> query = createJpqlQuery(SELECT_BY_REFRESH_ENABLED);
        query.setParameter("tokenRefreshEnabled", tokenRefreshEnabled);
        return query.getResultList();
    }

}
