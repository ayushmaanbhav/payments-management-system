package com.ayushmaanbhav.gatewayProvider.mapper;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.mapper.OneWayMapper;
import com.ayushmaanbhav.commons.util.LUUID;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayProviderConfigDto;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderConfig;
import com.ayushmaanbhav.gatewayProvider.entity.repository.GatewayConnectionSettingRepository;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GatewayProviderConfigMapper implements OneWayMapper<GatewayProviderConfigDto, GatewayProviderConfig> {
    GatewayConnectionSettingRepository connectionSettingRepository;

    @Override
    public @NonNull GatewayProviderConfig forward(@NonNull GatewayProviderConfigDto input) throws ApiException {
        return GatewayProviderConfig.builder()
                .id(null)
                .externalId(LUUID.generateUuid())
                .provider(input.getProvider())
                .merchantId(input.getMerchantId())
                .disabled(input.getDisabled())
                .disabledReason(input.getDisabledReason())
                .disabledDate(input.getDisabled() ? ZonedDateTime.now() : null)
                .connectionSetting(connectionSettingRepository.selectByExternalId(input.getConnectionSettingId()))
                .build();
    }
}
