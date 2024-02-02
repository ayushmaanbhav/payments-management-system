package com.ayushmaanbhav.requestIdempotency.api.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Jacksonized
public class ResolveRequestDto {
    @NonNull String mappedId;
    @NonNull String mappedIdType;
}
