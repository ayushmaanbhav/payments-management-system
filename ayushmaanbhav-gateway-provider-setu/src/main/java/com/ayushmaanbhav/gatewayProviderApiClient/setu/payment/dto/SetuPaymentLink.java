package com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@Jacksonized
public class SetuPaymentLink {
    @JsonProperty("shortURL") String shortUrl;
    @JsonProperty("upiID") String upiId;
    @JsonProperty("upiLink") String upiLink;
}
