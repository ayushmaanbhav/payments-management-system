package com.ayushmaanbhav.payment.testsetup.mock;

import com.azure.messaging.eventgrid.EventGridPublisherClient;
import com.ayushmaanbhav.commonsspring.api.event.EventApi;
import com.ayushmaanbhav.eventProcessor.dto.EventProcessorDtoApi;
import com.ayushmaanbhav.requestIdempotency.service.RequestIdempotencyService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Service;

@Service
public class MockSetup {

    @MockBean
    RequestIdempotencyService requestIdempotencyService;
    @MockBean
    EventApi eventApi;
    @MockBean
    EventProcessorDtoApi eventProcessorDtoApi;

}
