package com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ayushmaanbhav.commons.contstants.Currency;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@Jacksonized
public class SetuAmount {
    @NonNull @JsonProperty("value") Long normalisedAmount;
    @NonNull @JsonProperty("currencyCode") Currency currency;
}
