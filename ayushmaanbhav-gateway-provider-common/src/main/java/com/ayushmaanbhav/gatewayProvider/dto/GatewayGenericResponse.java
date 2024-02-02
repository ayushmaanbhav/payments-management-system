package com.ayushmaanbhav.gatewayProvider.dto;

import com.ayushmaanbhav.gatewayProvider.embedded.ApiEventDetail;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Jacksonized
public class GatewayGenericResponse<T> {
    @NonNull T pojo;
    @NonNull ApiEventDetail rawDetail;
}
