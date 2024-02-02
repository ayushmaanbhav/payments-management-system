package com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.constant.SetuPaymentStatus;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@Jacksonized
public class SetuCreatePaymentResponseData {
    @NonNull @JsonProperty("paymentLink") SetuPaymentLink paymentLink;
    @NonNull @JsonProperty("platformBillID") String providerOrderId;
}
