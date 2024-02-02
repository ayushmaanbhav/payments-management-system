package com.ayushmaanbhav.eventProcessor.api;

import com.ayushmaanbhav.commons.contstants.EventStatus;
import com.ayushmaanbhav.eventProcessor.db.pojo.Event;
import com.ayushmaanbhav.eventProcessor.testsetup.event.EventDataSetUp;
import com.ayushmaanbhav.eventProcessor.testsetup.event.EventSetup;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EventProcessorApiTest extends AbstractApiTest {

    @Autowired
    private EventProcessorApi eventProcessorApi;
    @Autowired
    private EventSetup eventSetup;

    @Test
    public void testCreate() {
        Event event = eventSetup.setupEvent();
        Event inDb = eventProcessorApi.getEventsByStatus(EventStatus.NEW).get(0);
        assertNotNull(inDb);
        assertEquals(event.getId(), inDb.getId());
        assertEquals(event.getIdempotencyId(), inDb.getIdempotencyId());
        assertEquals(event.getPayload(), inDb.getPayload());
    }

    @Test
    public void testCreateDuplicate() {
        Event event1 = EventDataSetUp.createEvent();
        eventProcessorApi.create(event1);
        List<Event> inDb = eventProcessorApi.getEventsByStatus(EventStatus.NEW);
        assertEquals(inDb.size(), 1);

        Event event2 = EventDataSetUp.createEvent();
        eventProcessorApi.create(event2);
        inDb = eventProcessorApi.getEventsByStatus(EventStatus.NEW);
        assertEquals(inDb.size(), 2);

        eventProcessorApi.create(event2);
        inDb = eventProcessorApi.getEventsByStatus(EventStatus.NEW);
        assertEquals(inDb.size(), 2);
    }

}
