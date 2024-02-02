package com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@Jacksonized
public class SetuErrorData {
    @NonNull @JsonProperty("code") String code;
    @NonNull @JsonProperty("detail") String detail;
    @NonNull @JsonProperty("docURL") String docUrl;
    @JsonProperty("errors") List<SetuErrorLineItem> errorLineItems;
    @NonNull @JsonProperty("title") String title;
    @NonNull @JsonProperty("traceID") String traceId;
}
