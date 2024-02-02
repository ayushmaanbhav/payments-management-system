package com.ayushmaanbhav.gatewayProvider.mapper;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.mapper.OneWayMapper;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayClientConnectionSetting;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayConnectionSetting;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GatewayClientTokenRefreshConnectionSettingMapper
        implements OneWayMapper<GatewayConnectionSetting, GatewayClientConnectionSetting> {

    @Override
    public @NonNull GatewayClientConnectionSetting forward(@NonNull GatewayConnectionSetting input) throws ApiException {
        var settings = input.getConnectionSetting();
        return GatewayClientConnectionSetting.builder()
                .key(settings.getKey())
                .secretToken(settings.getSecretToken())
                .baseUrl(settings.getBaseUrl())
                .refreshToken(settings.getRefreshToken())
                .provider(input.getProvider())
                // non required fields
                .merchantId(Strings.EMPTY)
                .refreshTokenEnabled(false)
                .build();
    }
}
