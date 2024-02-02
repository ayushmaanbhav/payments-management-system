package com.ayushmaanbhav.gatewayProvider.dto;

import com.ayushmaanbhav.commons.contstants.GatewayProvider;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Jacksonized
public class GatewayConnectionSettingDto {
    @NonNull GatewayProvider provider;
    @NonNull Boolean tokenRefreshEnabled;
    @NotNull String baseUrl;
    @NotNull String key;
    @NotNull String secretToken;
}
