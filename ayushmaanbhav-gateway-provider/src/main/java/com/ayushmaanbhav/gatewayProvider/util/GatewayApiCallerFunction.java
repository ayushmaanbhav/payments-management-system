package com.ayushmaanbhav.gatewayProvider.util;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayClientConnectionSetting;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayRefreshTokenRequest;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderEvent;
import com.ayushmaanbhav.gatewayProviderApiClient.ApiClient;
import org.springframework.data.util.Pair;

@FunctionalInterface
public interface GatewayApiCallerFunction {
    Pair<String, GatewayProviderEvent> call(
            ApiClient<GatewayRefreshTokenRequest, String> apiClient,
            GatewayClientConnectionSetting connectionSetting,
            GatewayRefreshTokenRequest request
    ) throws ApiException;
}
