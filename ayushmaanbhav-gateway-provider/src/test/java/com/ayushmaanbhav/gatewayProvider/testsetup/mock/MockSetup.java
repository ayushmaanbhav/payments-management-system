package com.ayushmaanbhav.gatewayProvider.testsetup.mock;

import com.ayushmaanbhav.commonsspring.api.event.EventApi;
import com.ayushmaanbhav.payment.api.payment.PaymentWebhookApi;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Service;

@Service
public class MockSetup {
    @MockBean
    PaymentWebhookApi paymentWebhookApi;
    @MockBean
    EventApi eventApi;
}
