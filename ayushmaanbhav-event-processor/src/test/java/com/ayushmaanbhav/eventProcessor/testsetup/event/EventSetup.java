package com.ayushmaanbhav.eventProcessor.testsetup.event;

import com.ayushmaanbhav.eventProcessor.db.dao.EventDao;
import com.ayushmaanbhav.eventProcessor.db.pojo.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventSetup {

    @Autowired
    private EventDao eventDao;

    public Event setupEvent() {
        Event event = EventDataSetUp.createEvent();
        eventDao.save(event);
        return event;
    }

}
