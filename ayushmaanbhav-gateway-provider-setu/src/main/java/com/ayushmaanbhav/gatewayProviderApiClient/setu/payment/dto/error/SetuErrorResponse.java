package com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@Jacksonized
public class SetuErrorResponse {
    @NonNull @JsonProperty("status") Integer requestStatus;
    @NonNull @JsonProperty("success") Boolean requestSuccess;
    @NonNull @JsonProperty("error") SetuErrorData errorData;
}
