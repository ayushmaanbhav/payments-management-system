package com.ayushmaanbhav.payment.api.payment.dto;

import com.ayushmaanbhav.commons.contstants.PaymentOrderType;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Jacksonized
public class PaymentOrderRequest {
    @NonNull String requestId;
    @NonNull PaymentOrderType type;
    @NonNull List<PaymentOrderRequestLineItem> lineItems;
    @NonNull String source;
    @NonNull String sourceService;
}
