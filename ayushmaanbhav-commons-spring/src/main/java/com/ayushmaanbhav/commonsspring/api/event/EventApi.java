package com.ayushmaanbhav.commonsspring.api.event;

import com.azure.core.exception.AzureException;
import com.azure.core.models.CloudEvent;
import com.azure.core.models.CloudEventDataFormat;
import com.azure.core.util.BinaryData;
import com.azure.messaging.eventgrid.EventGridPublisherClient;
import com.ayushmaanbhav.commonsspring.api.AbstractApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventApi extends AbstractApi {

    @Autowired
    private EventGridPublisherClient<CloudEvent> eventGridPublisherClient;
    private static final String EVENT_SOURCE = "ayushmaanbhav-Server";

    public <T> void publishEvent(T t, String eventType, String id) throws AzureException {
        CloudEvent event = new CloudEvent(EVENT_SOURCE, eventType,
                BinaryData.fromObject(t), CloudEventDataFormat.JSON, "application/json");
        event.setId(id);
        eventGridPublisherClient.sendEvent(event);
    }

}
