package com.ayushmaanbhav.eventProcessor.api;

import com.ayushmaanbhav.commons.contstants.EventStatus;
import com.ayushmaanbhav.commonsspring.api.AbstractApi;
import com.ayushmaanbhav.eventProcessor.db.dao.EventDao;
import com.ayushmaanbhav.eventProcessor.db.pojo.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class EventProcessorApi extends AbstractApi {

    private final EventDao eventDao;

    public void create(Event event) {
        Event inDb = find(event);
        if (inDb == null) {
            eventDao.save(event);
        }
    }

    public List<Event> getEventsByStatus(EventStatus eventStatus) {
        return eventDao.selectByStatus(eventStatus);
    }

    public void updateRetryCount(Long id) {
        Event event = eventDao.select(id);
        event.setRetryCount(event.getRetryCount() + 1);
    }

    public void updateStatus(Long id, EventStatus status) {
        Event event = eventDao.select(id);
        event.setStatus(status);
    }

    private Event find(Event event) {
        return eventDao.selectByIdempotencyId(event.getIdempotencyId());
    }

}
