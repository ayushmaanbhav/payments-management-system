package com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.ZonedDateTime;
@Value
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@Jacksonized
public class SetuTokenData {
    @JsonProperty("expiresIn") Integer expiresIn;
    @NonNull @JsonProperty("token") String token;
}
