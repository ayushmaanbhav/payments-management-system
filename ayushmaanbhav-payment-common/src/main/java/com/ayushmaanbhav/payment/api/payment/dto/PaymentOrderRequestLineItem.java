package com.ayushmaanbhav.payment.api.payment.dto;

import com.ayushmaanbhav.commons.contstants.Currency;
import com.ayushmaanbhav.commons.data.ContextParam;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Jacksonized
public class PaymentOrderRequestLineItem {
    @NonNull Long normalisedAmount;
    @NonNull Currency currency;
    ContextParam contextParam;
}
