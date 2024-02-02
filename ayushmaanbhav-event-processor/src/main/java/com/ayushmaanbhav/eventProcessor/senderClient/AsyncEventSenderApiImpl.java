package com.ayushmaanbhav.eventProcessor.senderClient;

import com.azure.core.exception.AzureException;
import com.ayushmaanbhav.commons.request.AsyncEventRequest;
import com.ayushmaanbhav.commonsspring.api.event.EventApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AsyncEventSenderApiImpl implements AsyncEventSenderApi {

    private final EventApi eventApi;

    @Override
    public void send(AsyncEventRequest asyncEventRequest) throws AzureException {
        eventApi.publishEvent(asyncEventRequest.getPayload(), asyncEventRequest.getType(),
                asyncEventRequest.getCorrelationId());
    }

}
