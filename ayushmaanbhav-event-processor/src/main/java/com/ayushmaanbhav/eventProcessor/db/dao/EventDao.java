package com.ayushmaanbhav.eventProcessor.db.dao;

import com.ayushmaanbhav.commons.contstants.EventStatus;
import com.ayushmaanbhav.commonsspring.db.dao.AbstractDao;
import com.ayushmaanbhav.eventProcessor.db.pojo.Event;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class EventDao extends AbstractDao<Event> {

    private static final String SELECT_BY_STATUS = "SELECT e FROM Event e WHERE " +
            "status = :status ORDER BY id ";
    private static final String SELECT_BY_IDEMPOTENCY_ID = "SELECT e FROM Event e WHERE " +
            "idempotencyId = :idempotencyId";

    public EventDao() {
        super(Event.class);
    }

    public List<Event> selectByStatus(EventStatus status) {
        TypedQuery<Event> typedQuery = createJpqlQuery(SELECT_BY_STATUS);
        typedQuery.setParameter("status", status);
        return typedQuery.getResultList();
    }

    public Event selectByIdempotencyId(String idempotencyId) {
        TypedQuery<Event> typedQuery = createJpqlQuery(SELECT_BY_IDEMPOTENCY_ID);
        typedQuery.setParameter("idempotencyId", idempotencyId);
        return selectSingleOrNone(typedQuery);
    }

}
