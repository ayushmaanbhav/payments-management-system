package com.ayushmaanbhav.gatewayProvider.mapper;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.mapper.OneWayMapper;
import com.ayushmaanbhav.commons.util.LUUID;
import com.ayushmaanbhav.gatewayProvider.dto.ConnectionSettingDto;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayConnectionSettingDto;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayConnectionSetting;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GatewayConnectionSettingMapper implements OneWayMapper<GatewayConnectionSettingDto, GatewayConnectionSetting> {
    @Override
    public @NonNull GatewayConnectionSetting forward(@NonNull GatewayConnectionSettingDto input) throws ApiException {
        return GatewayConnectionSetting.builder()
                .id(null)
                .externalId(LUUID.generateUuid())
                .provider(input.getProvider())
                .connectionSetting(ConnectionSettingDto.builder()
                        .baseUrl(input.getBaseUrl())
                        .key(input.getKey())
                        .secretToken(input.getSecretToken())
                        .build())
                .tokenRefreshEnabled(input.getTokenRefreshEnabled())
                .retryCount(0)
                .lastTokenRefreshDate(null)
                .build();
    }
}
