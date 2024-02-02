package com.ayushmaanbhav.commons.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.ZonedDateTime;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Jacksonized
public class PaymentConfirmWebhookRequest {
    @NonNull @JsonProperty("paymentOrderId") String paymentOrderId;
    @NonNull @JsonProperty("status") PaymentStatus status;
    @JsonProperty("transactionId") String transactionId;
    @JsonProperty("paidDate") ZonedDateTime paidDate;
    @JsonProperty("expiredDate") ZonedDateTime expiredDate;
}
