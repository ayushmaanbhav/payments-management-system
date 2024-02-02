package com.ayushmaanbhav.eventProcessor.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ayushmaanbhav.commons.contstants.EventStatus;
import com.ayushmaanbhav.commons.contstants.EventType;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.response.EventProcessorResponse;
import com.ayushmaanbhav.commonsspring.requestMetadata.RequestMetadata;
import com.ayushmaanbhav.eventProcessor.api.EventProcessorApi;
import com.ayushmaanbhav.eventProcessor.api.EventWorkflowApi;
import com.ayushmaanbhav.eventProcessor.db.pojo.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class EventProcessorDtoApi {

    private final EventProcessorApi eventProcessorApi;
    private final EventWorkflowApi eventWorkflowApi;
    private final ObjectMapper objectMapper;

    public <T> void createEvent(T t, EventType eventType, String idempotencyId, String correlationId) {
        eventProcessorApi.create(getEvent(t, eventType, idempotencyId, correlationId));
    }

    public <T> void createEvent(T t, EventType eventType, String idempotencyId) throws ApiException {
        var correlationId = RequestMetadata.getValue(RequestMetadata.Header.CORRELATION_ID);
        createEvent(t, eventType, idempotencyId, correlationId);
    }

    public EventProcessorResponse processEvent() {
        log.info("Event processing started");
        List<Event> eventList = eventProcessorApi.getEventsByStatus(EventStatus.NEW);
        int totalCount = eventList.size(), successCount = 0;
        for (Event event : eventList) {
            RequestMetadata.setValue(RequestMetadata.Header.CORRELATION_ID, event.getCorrelationId());
            boolean isSuccess = eventWorkflowApi.processEvent(event);
            RequestMetadata.clear(RequestMetadata.Header.CORRELATION_ID);
            if (isSuccess) {
                successCount++;
            }
        }
        log.info("Total events processed,success count,failure count = "
                + totalCount + "," + successCount + "," + (totalCount - successCount));
        return getEventProcessResponse(totalCount-successCount);
    }

    private <T> Event getEvent(T t, EventType eventType, String idempotencyId, String correlationId) {
        Event event = new Event();
        event.setIdempotencyId(idempotencyId);
        event.setCorrelationId(correlationId);
        event.setType(eventType.toString());
        event.setPayload(objectMapper.valueToTree(t));
        event.setRetryCount(0);
        event.setStatus(EventStatus.NEW);
        return event;
    }

    private EventProcessorResponse getEventProcessResponse(int count) {
        EventProcessorResponse response = new EventProcessorResponse();
        response.setFailureCount(count);
        return response;
    }

}
