package com.ayushmaanbhav.gatewayProvider.dto;

import com.ayushmaanbhav.commons.contstants.GatewayProvider;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Jacksonized
public class GatewayClientConnectionSetting {
    @NonNull GatewayProvider provider;
    @NonNull String merchantId;
    @NonNull String baseUrl;
    @NonNull String key;
    @NonNull String secretToken;
    @NonNull Boolean refreshTokenEnabled;
    String refreshToken;
}

