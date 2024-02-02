package com.ayushmaanbhav.commons.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@Jacksonized
public class PaymentClientResponse {

    @NonNull @JsonProperty("data")
    PaymentClientDataResponse data;
}
