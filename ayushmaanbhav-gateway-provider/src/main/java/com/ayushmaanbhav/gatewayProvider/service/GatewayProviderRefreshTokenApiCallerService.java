package com.ayushmaanbhav.gatewayProvider.service;

import com.ayushmaanbhav.commons.contstants.GatewayProvider;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayClientConnectionSetting;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayRefreshTokenRequest;
import com.ayushmaanbhav.gatewayProvider.util.GatewayApiCallerFunction;
import com.ayushmaanbhav.gatewayProviderApiClient.ApiClient;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GatewayProviderRefreshTokenApiCallerService {
    Map<GatewayProvider, ApiClient<GatewayRefreshTokenRequest, String>> refreshTokenApiClients;

    @Autowired
    public GatewayProviderRefreshTokenApiCallerService(
            @NonNull List<ApiClient<GatewayRefreshTokenRequest, String>> refreshTokenApiClients
    ) {
        this.refreshTokenApiClients = new HashMap<>();
        for (var apiClient : refreshTokenApiClients) {
            this.refreshTokenApiClients.put(apiClient.getGatewayProvider(), apiClient);
        }
    }

    public @NonNull String refresh(@NonNull GatewayClientConnectionSetting connectionSetting,
                                   @NonNull GatewayApiCallerFunction apiCaller) throws ApiException {
        var apiClient = refreshTokenApiClients.get(connectionSetting.getProvider());
        var newSettingsResponse = apiCaller
                .call(apiClient, connectionSetting, new GatewayRefreshTokenRequest());
        return newSettingsResponse.getFirst();
    }
}
