package com.ayushmaanbhav.eventProcessor.api;

import com.azure.core.exception.AzureException;
import com.ayushmaanbhav.commons.contstants.EventStatus;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.request.AsyncEventRequest;
import com.ayushmaanbhav.eventProcessor.db.pojo.Event;
import com.ayushmaanbhav.eventProcessor.senderClient.AsyncEventSenderApi;
import com.ayushmaanbhav.eventProcessor.spring.EventProcessorApplicationProperties;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Log4j2
public class EventWorkflowApi {

    private final EventProcessorApplicationProperties applicationProperties;
    private final EventProcessorApi eventProcessorApi;
    private final AsyncEventSenderApi asyncEventSenderApi;

    public boolean processEvent(Event event) {
        if (event.getRetryCount() > applicationProperties.getLogicAppMaxRetries()) {
            updateStatus(event, EventStatus.FAIL);
            log.info("Event processing failed for the Event with id : " + event.getId());
            return false;
        }

        try {
            asyncEventSenderApi.send(getAsyncEventRequest(event));
            updateStatus(event, EventStatus.DONE);
            return true;
        } catch (AzureException ex) {
            updateRetryCount(event);
            log.error("Event with id " + event.getId() +
                    " got an AzureException occurred: " + ex.getMessage());
        } catch (ApiException ex) {
            updateRetryCount(event);
            log.error("Event with id " + event.getId() +
                    " got an exception occurred: " + ex.getErrorCode() + ", " + ex.getMessage());
        }
        return false;
    }

    private void updateRetryCount(Event event) {
        eventProcessorApi.updateRetryCount(event.getId());
    }

    private void updateStatus(Event event, EventStatus status) {
        eventProcessorApi.updateStatus(event.getId(), status);
    }

    private <T> AsyncEventRequest getAsyncEventRequest(Event event) {
        AsyncEventRequest asyncEventRequest = new AsyncEventRequest();
        asyncEventRequest.setCorrelationId(event.getCorrelationId());
        asyncEventRequest.setType(event.getType());
        asyncEventRequest.setPayload(event.getPayload());
        return asyncEventRequest;
    }

}
