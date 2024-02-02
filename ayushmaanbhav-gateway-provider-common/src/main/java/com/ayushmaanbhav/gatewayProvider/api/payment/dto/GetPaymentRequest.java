package com.ayushmaanbhav.gatewayProvider.api.payment.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Jacksonized
public class GetPaymentRequest {
    @NonNull String orderId;
    @NonNull String gatewayProviderConfigId;
    @NonNull String providerOrderId;
    String providerTransactionId;
    String providerSessionId;
}
