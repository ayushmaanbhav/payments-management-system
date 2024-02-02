package com.ayushmaanbhav.commons.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentDetailsResponse {
    @NonNull PaymentStatus status;
    String paymentWebUrl;
    String paymentDeepLink;
    String transactionId;
    String message;
    String paymentOrderId;
}
