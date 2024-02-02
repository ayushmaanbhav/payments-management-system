package com.ayushmaanbhav.gatewayProvider.controller;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayConnectionSetting;
import com.ayushmaanbhav.gatewayProvider.entity.repository.GatewayConnectionSettingRepository;
import com.ayushmaanbhav.gatewayProvider.service.GatewayConnectionSettingService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Log4j2
@RestController
@RequestMapping("gatewayTokenRefresh/cron")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GatewayTokenRefreshController {
    GatewayConnectionSettingRepository gatewayConnectionSettingRepository;
    GatewayConnectionSettingService gatewayConnectionSettingService;

    @PostMapping
    public void refreshTokens() throws ApiException {
        log.info("beginning to refresh tokens");
        List<GatewayConnectionSetting> gatewayConnectionSettings =
                gatewayConnectionSettingRepository.selectByRefreshEnabled(true);
        for(GatewayConnectionSetting setting : gatewayConnectionSettings) {
            try {
                gatewayConnectionSettingService.refreshToken(setting.getExternalId());
            } catch (ApiException e) {
                log.error("failed to refresh token for id: {} provider : {}",
                        setting.getExternalId(), setting.getProvider(), e);
            }
        }
        log.info("refresh tokens ended");
    }
}
