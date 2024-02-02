package com.ayushmaanbhav.payment.api.payment;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.PaymentResponse;
import lombok.NonNull;

public interface PaymentWebhookApi {

    void processPaymentWebhook(@NonNull PaymentResponse paymentResponse) throws ApiException;
}
