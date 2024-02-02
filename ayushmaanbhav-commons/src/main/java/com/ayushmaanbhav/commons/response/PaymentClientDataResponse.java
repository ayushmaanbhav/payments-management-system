package com.ayushmaanbhav.commons.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.ZonedDateTime;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@Jacksonized
public class PaymentClientDataResponse {
    @NonNull @JsonProperty("paymentOrderId") String paymentOrderId;
    @NonNull @JsonProperty("status") PaymentStatus status;
    @JsonProperty("paymentWebUrl") String paymentWebUrl;
    @JsonProperty("paymentDeepLink") String paymentDeepLink;
    @JsonProperty("transactionId") String transactionId;
    @JsonProperty("paidDate") ZonedDateTime  paidDate;
    @JsonProperty("expiredDate") ZonedDateTime expiredDate;
}
