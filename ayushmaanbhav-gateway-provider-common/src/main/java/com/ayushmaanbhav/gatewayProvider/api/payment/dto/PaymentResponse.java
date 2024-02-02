package com.ayushmaanbhav.gatewayProvider.api.payment.dto;

import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.ZonedDateTime;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Jacksonized
public class PaymentResponse {
    @NonNull PaymentStatus status;
    @NonNull String orderId;
    @NonNull String providerOrderId;
    @NonNull String providerStatus;
    Long normalisedPaidAmount;
    String paymentWebUrl;
    String paymentDeepLink;
    String providerSessionId;
    String providerTransactionId;
    String providerPaymentMethod;
    ZonedDateTime providerPaidDate;
    String providerErrorCode;
    String providerErrorDescription;
}
