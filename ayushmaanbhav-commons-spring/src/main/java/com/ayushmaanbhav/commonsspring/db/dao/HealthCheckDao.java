package com.ayushmaanbhav.commonsspring.db.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class HealthCheckDao {

    private static final String HEALTH_CHECK = "SELECT 1";
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public void getHealthCheck() {
        Query nativeQuery = entityManager.createNativeQuery(HEALTH_CHECK);
        nativeQuery.getSingleResult();
    }

}
 