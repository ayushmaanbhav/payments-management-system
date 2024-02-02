package com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@Jacksonized
public class SetuErrorLineItem {
    @NonNull @JsonProperty("code") String code;
    @NonNull @JsonProperty("detail") String detail;
    @NonNull @JsonProperty("docURL") String docUrl;
    @NonNull @JsonProperty("title") String title;
}
