package com.ayushmaanbhav.gatewayProvider.mapper;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.mapper.OneWayMapper;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayClientConnectionSetting;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderConfig;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GatewayClientConnectionSettingMapper
        implements OneWayMapper<GatewayProviderConfig, GatewayClientConnectionSetting> {

    @Override
    public @NonNull GatewayClientConnectionSetting forward(@NonNull GatewayProviderConfig input) throws ApiException {
        var settings = input.getConnectionSetting().getConnectionSetting();
        return GatewayClientConnectionSetting.builder()
                .key(settings.getKey())
                .secretToken(settings.getSecretToken())
                .baseUrl(settings.getBaseUrl())
                .merchantId(input.getMerchantId())
                .refreshTokenEnabled(input.getConnectionSetting().getTokenRefreshEnabled())
                .refreshToken(settings.getRefreshToken())
                .provider(input.getProvider())
                .build();
    }
}
