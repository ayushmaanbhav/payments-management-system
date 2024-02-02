package com.ayushmaanbhav.commonsspring.db.dao;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public abstract class AbstractDao<T> {

    @PersistenceContext
    private EntityManager em;
    private Class<T> clazz;

    public AbstractDao(Class clazz) {
        this.clazz = clazz;
    }

    @Transactional(readOnly = true)
    public void save(T t) {
        em.persist(t);
    }

    @Transactional
    public T select(Object id) {
        return em.find(clazz, id);
    }

    public void update(T t) {

    }

    public void update(List<T> list) {}

    protected TypedQuery<T> createJpqlQuery(String query) {
        return em.createQuery(query, clazz);
    }

    protected T selectSingleOrNone(TypedQuery<T> query) {
        query.setMaxResults(1);
        List<T> list = query.getResultList();
        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }


}
