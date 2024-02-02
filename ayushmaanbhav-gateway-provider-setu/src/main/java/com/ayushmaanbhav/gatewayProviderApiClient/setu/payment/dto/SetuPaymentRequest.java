package com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@Jacksonized
public class SetuPaymentRequest {
    @NonNull @JsonProperty("billerBillID") String orderId;
    @NonNull @JsonProperty("expiryDate") String expiryDate;
    @NonNull @JsonProperty("amount") SetuAmount amount;
    @NonNull @JsonProperty("amountExactness") String amountExactness;
}
