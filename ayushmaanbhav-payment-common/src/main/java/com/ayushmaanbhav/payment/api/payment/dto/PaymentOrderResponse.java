package com.ayushmaanbhav.payment.api.payment.dto;

import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.ZonedDateTime;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Jacksonized
public class PaymentOrderResponse {
    @NonNull String paymentOrderId;
    @NonNull PaymentStatus status;
    String paymentWebUrl;
    String paymentDeepLink;
    String transactionId;
    ZonedDateTime paidDate;
    ZonedDateTime expiredDate;
}
