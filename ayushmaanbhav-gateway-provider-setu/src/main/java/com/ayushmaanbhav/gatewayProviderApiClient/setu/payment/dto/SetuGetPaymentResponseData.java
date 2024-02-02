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
public class SetuGetPaymentResponseData {
    @JsonProperty("billerBillID") String orderId;
    @JsonProperty("createdAt") String createdAt;
    @JsonProperty("expiresAt") String expiresAt;
    @NonNull @JsonProperty("paymentLink") SetuPaymentLink paymentLink;
    @NonNull @JsonProperty("platformBillID") String providerOrderId;
    @NonNull @JsonProperty("status") SetuPaymentStatus status;
    @JsonProperty("amountPaid") SetuAmount amount;
    @JsonProperty("transactionNote") String note;
}
