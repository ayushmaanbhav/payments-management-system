package com.ayushmaanbhav.gatewayProvider.service;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import com.ayushmaanbhav.commons.util.ApiUtil;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayProviderConfigDto;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayConnectionSetting;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderConfig;
import com.ayushmaanbhav.gatewayProvider.entity.repository.GatewayProviderConfigRepository;
import com.ayushmaanbhav.gatewayProvider.mapper.GatewayProviderConfigMapper;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j2
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class GatewayProviderConfigService {
    GatewayProviderConfigRepository providerConfigRepository;
    GatewayProviderConfigMapper gatewayProviderConfigMapper;
    GatewayConnectionSettingService gatewayConnectionSettingService;

    public @NonNull GatewayProviderConfig get(@NonNull String externalId) throws ApiException {
        GatewayProviderConfig gatewayProviderConfig = providerConfigRepository.selectByExternalId(externalId);
        if(gatewayProviderConfig == null) {
            log.error("Gateway Provider Config not found for Id");
            throw new ApiException("config not found for id", ErrorCode.NOT_FOUND);
        }
        return gatewayProviderConfig;
    }

    public String save(@NonNull GatewayProviderConfigDto configDto) throws ApiException {
        if (configDto.getDisabled()) {
            ApiUtil.verifyExistence(configDto.getDisabledReason(), "disabled_reason is required");
        } else {
            configDto = configDto.toBuilder().disabledReason(null).build();
        }
        GatewayConnectionSetting connectionSetting = gatewayConnectionSettingService
                .getByExternalId(configDto.getConnectionSettingId());
        ApiUtil.verifyExistence(connectionSetting, "provide a valid connection_setting_id");
        ApiUtil.verifyTruth(connectionSetting.getProvider() == configDto.getProvider(),
                "connection_setting should have same provider as config");
        GatewayProviderConfig config = gatewayProviderConfigMapper.forward(configDto);
        providerConfigRepository.save(config);
        return config.getExternalId();
    }
}
