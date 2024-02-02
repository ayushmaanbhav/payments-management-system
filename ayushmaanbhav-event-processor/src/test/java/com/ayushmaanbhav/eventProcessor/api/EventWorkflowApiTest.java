package com.ayushmaanbhav.eventProcessor.api;

import com.azure.core.exception.AzureException;
import com.ayushmaanbhav.commons.contstants.EventStatus;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.eventProcessor.db.pojo.Event;
import com.ayushmaanbhav.eventProcessor.senderClient.AsyncEventSenderApi;
import com.ayushmaanbhav.eventProcessor.testsetup.event.EventDataSetUp;
import com.ayushmaanbhav.eventProcessor.testsetup.event.EventSetup;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

public class EventWorkflowApiTest extends AbstractApiTest {

    @Autowired
    private EventWorkflowApi eventWorkflowApi;
    @Autowired
    private EventProcessorApi eventProcessorApi;
    @Autowired
    private EventSetup eventSetup;
    @Mock
    private AsyncEventSenderApi asyncEventSenderApi;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(eventWorkflowApi, "asyncEventSenderApi", asyncEventSenderApi);
    }

    @Test
    public void testEventProcessSuccess() {
        Event event = eventSetup.setupEvent();
        Event inDb = eventProcessorApi.getEventsByStatus(EventStatus.NEW).get(0);
        assertEquals(event.getStatus(), inDb.getStatus());
        boolean check = eventWorkflowApi.processEvent(event);
        assertTrue(check);
        assertEquals(event.getStatus(), EventStatus.DONE);
    }

    @Test
    public void testEventProcessFailure() throws ApiException {
        doThrow(new AzureException("Error publishing event"))
                .when(asyncEventSenderApi).send(any());

        Event event = eventSetup.setupEvent();
        assertEquals(event.getStatus(), EventStatus.NEW);

        boolean check = eventWorkflowApi.processEvent(event);
        assertFalse(check);
        assertEquals(event.getStatus(), EventStatus.NEW);
        assertEquals(event.getRetryCount(), 1);

        check = eventWorkflowApi.processEvent(event);
        assertFalse(check);
        assertEquals(event.getStatus(), EventStatus.NEW);
        assertEquals(event.getRetryCount(), 2);
    }

    @Test
    public void testMaxRetries() {
        Event event = EventDataSetUp.createEvent();
        event.setRetryCount(6);
        eventProcessorApi.create(event);
        assertEquals(event.getRetryCount(), 6);

        boolean check = eventWorkflowApi.processEvent(event);
        assertFalse(check);
        assertEquals(event.getStatus(), EventStatus.FAIL);
    }

}
