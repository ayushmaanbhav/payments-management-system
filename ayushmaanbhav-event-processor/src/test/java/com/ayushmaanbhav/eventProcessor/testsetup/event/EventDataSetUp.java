package com.ayushmaanbhav.eventProcessor.testsetup.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ayushmaanbhav.commons.contstants.EventStatus;
import com.ayushmaanbhav.eventProcessor.db.pojo.Event;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventDataSetUp {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Event createEvent() {
        Event event = new Event();
        event.setIdempotencyId(UUID.randomUUID().toString());
        event.setCorrelationId("1");
        event.setPayload(objectMapper.valueToTree("{\"name\":\"Pavan\"}")); //dummy object
        event.setType("type");
        event.setStatus(EventStatus.NEW);
        event.setRetryCount(0);
        return event;
    }

}
