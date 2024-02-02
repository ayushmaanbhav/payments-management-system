package com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@Jacksonized
public class SetuTokenResponse {
    @NonNull @JsonProperty("status") Integer requestStatus;
    @NonNull @JsonProperty("success") Boolean requestSuccess;
    @NonNull @JsonProperty("data") SetuTokenData data;
}
