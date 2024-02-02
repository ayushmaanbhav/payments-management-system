package com.ayushmaanbhav.gatewayProvider.dto;

import com.ayushmaanbhav.commons.contstants.GatewayProvider;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Jacksonized
public class GatewayProviderConfigDto {
    @NonNull GatewayProvider provider;
    @NonNull String merchantId;
    @NonNull String connectionSettingId;
    @NonNull Boolean disabled;
    String disabledReason;
}
