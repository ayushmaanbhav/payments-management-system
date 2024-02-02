package com.ayushmaanbhav.gatewayProvider.api.payment.dto;

import com.ayushmaanbhav.commons.contstants.Currency;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Jacksonized
public class CreatePaymentRequest {
    @NonNull String orderId;
    @NonNull String gatewayProviderConfigId;
    @NonNull Long normalisedAmount;
    @NonNull Currency currency;
    @NonNull Integer expiresInSecond;
    @NonNull Map<String, String> params;
    @NonNull String customerPhone;
}
