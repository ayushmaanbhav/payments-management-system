package com.ayushmaanbhav.gatewayProvider.service;

import com.ayushmaanbhav.commons.contstants.GatewayProvider;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import com.ayushmaanbhav.commons.util.ApiUtil;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayConnectionSettingDto;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayConnectionSetting;
import com.ayushmaanbhav.gatewayProvider.entity.repository.GatewayConnectionSettingRepository;
import com.ayushmaanbhav.gatewayProvider.mapper.GatewayClientTokenRefreshConnectionSettingMapper;
import com.ayushmaanbhav.gatewayProvider.mapper.GatewayConnectionSettingMapper;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class GatewayConnectionSettingService {
    GatewayProviderApiCallerService gatewayProviderApiCallerService;
    GatewayConnectionSettingRepository gatewayConnectionSettingRepository;
    GatewayProviderRefreshTokenApiCallerService refreshTokenApiCallerService;
    GatewayConnectionSettingMapper gatewayConnectionSettingMapper;
    GatewayClientTokenRefreshConnectionSettingMapper tokenRefreshConnectionSettingMapper;
    Map<GatewayProvider, Long> refreshTokenExpiryConfig;

    @Autowired
    public GatewayConnectionSettingService(
            @NonNull GatewayProviderApiCallerService gatewayProviderApiCallerService,
            @NonNull GatewayConnectionSettingRepository gatewayConnectionSettingRepository,
            @NonNull GatewayProviderRefreshTokenApiCallerService refreshTokenApiCallerService,
            @NonNull GatewayConnectionSettingMapper gatewayConnectionSettingMapper,
            @NonNull GatewayClientTokenRefreshConnectionSettingMapper tokenRefreshConnectionSettingMapper,
            @NonNull @Value("#{${ayushmaanbhav.gateway.provider.refresh.token.expiry.seconds}}") Map<GatewayProvider, Long> expiryConfig
    ) {
        this.gatewayProviderApiCallerService = gatewayProviderApiCallerService;
        this.gatewayConnectionSettingMapper = gatewayConnectionSettingMapper;
        this.refreshTokenApiCallerService = refreshTokenApiCallerService;
        this.gatewayConnectionSettingRepository = gatewayConnectionSettingRepository;
        this.tokenRefreshConnectionSettingMapper = tokenRefreshConnectionSettingMapper;
        long errorCount = expiryConfig.entrySet().stream()
                .filter(it -> it.getKey() == null || it.getValue() == null || it.getValue() <= 0).count();
        if (errorCount > 0) {
            throw new RuntimeException("errors found in gateway refresh token expiry config");
        }
        this.refreshTokenExpiryConfig = expiryConfig;
    }

    public @NonNull GatewayConnectionSetting getByExternalId(@NonNull String externalId) throws ApiException {
        GatewayConnectionSetting setting = gatewayConnectionSettingRepository.selectByExternalId(externalId);
        ApiUtil.verifyExistence(setting, "setting not found");
        return setting;
    }

    public @NonNull String save(@NonNull GatewayConnectionSettingDto settingDto) throws ApiException {
        GatewayConnectionSetting setting = gatewayConnectionSettingMapper.forward(settingDto);
        gatewayConnectionSettingRepository.save(setting);
        return setting.getExternalId();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class, noRollbackFor = ApiException.class)
    public void refreshToken(String externalId) throws ApiException {
        GatewayConnectionSetting cSetting = getByExternalId(externalId);
        if(cSetting.getTokenRefreshEnabled()) {
            ZonedDateTime lastRefreshDate = cSetting.getLastTokenRefreshDate();
            ZonedDateTime currentDate = ZonedDateTime.now();
            Long expiryTimeSeconds = refreshTokenExpiryConfig.get(cSetting.getProvider());
            if (expiryTimeSeconds == null) {
                log.info("refresh token expiry config not found for connection setting id : {}, provider: {}",
                        externalId, cSetting.getProvider());
                throw new ApiException("refresh token expiry config not found for provider " + cSetting.getProvider(),
                        ErrorCode.INTERNAL_SERVER_ERROR);
            }
            if(lastRefreshDate == null ||
                    Duration.between(lastRefreshDate, currentDate).getSeconds() >= expiryTimeSeconds) {
                try {
                    String updatedToken = refreshTokenApiCallerService.refresh(
                            tokenRefreshConnectionSettingMapper.forward(cSetting),
                            gatewayProviderApiCallerService::call);
                    cSetting.setLastTokenRefreshDate(currentDate);
                    cSetting.getConnectionSetting().setRefreshToken(updatedToken);
                    cSetting.setRetryCount(0);
                } catch (ApiException e) {
                    log.error("Error occurred while calling refresh token, updating retry count", e);
                    cSetting.setRetryCount(cSetting.getRetryCount() + 1);
                    throw e;
                } finally {
                    gatewayConnectionSettingRepository.update(cSetting);
                }
            } else {
                log.info("no need to refresh token for connection setting id : {}, provider: {}",
                        externalId, cSetting.getProvider());
            }
        } else {
            log.info("refresh token disabled for connection setting id : {}, provider: {}",
                    externalId, cSetting.getProvider());
        }
    }
}
