package com.ayushmaanbhav.gatewayProvider.service;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayClientConnectionSetting;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayGenericResponse;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderEvent;
import com.ayushmaanbhav.gatewayProvider.entity.repository.GatewayProviderEventRepository;
import com.ayushmaanbhav.gatewayProvider.exception.GatewayGenericApiException;
import com.ayushmaanbhav.gatewayProviderApiClient.ApiClient;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GatewayProviderApiCallerService {
    GatewayProviderEventRepository providerEventRepository;
    GatewayProviderRefreshTokenApiCallerService refreshTokenService;

    public <Req, Res> @NonNull Pair<Res, GatewayProviderEvent> call(
            @NonNull ApiClient<Req, Res> apiClient,
            @NonNull GatewayClientConnectionSetting connectionSetting, @NonNull Req request
    ) throws ApiException {
        return this.callInternal(apiClient, connectionSetting, request, true);
    }

    private <Req, Res> @NonNull Pair<Res, GatewayProviderEvent> callInternal(
            @NonNull ApiClient<Req, Res> apiClient,
            @NonNull GatewayClientConnectionSetting connectionSetting, @NonNull Req request,
            boolean retryEnabled
    ) throws ApiException {
        GatewayGenericResponse<Res> response = null;
        GatewayProviderEvent.GatewayProviderEventBuilder providerEventBuilder = GatewayProviderEvent.builder();
        try {
            response = apiClient.call(connectionSetting, request);
        } catch (GatewayGenericApiException e) {
            if (shouldFetchRefreshTokenAndRetry(e, connectionSetting, retryEnabled)) {
                log.info("Refreshing token and trying again for provider: " + connectionSetting.getProvider());
                String newRefreshToken = refreshTokenService.refresh(connectionSetting, this::call);
                var newConnectionSetting = connectionSetting.toBuilder().refreshToken(newRefreshToken).build();
                return this.callInternal(apiClient, newConnectionSetting, request, false);
            }
            providerEventBuilder.rawDetail(e.getApiEventDetail())
                    .errorCode(e.getErrorCode().name())
                    .errorDescription(e.getMessage());
            throw e;
        } catch (ApiException e) {
            providerEventBuilder.errorCode(e.getErrorCode().name())
                    .errorDescription(e.getMessage());
            throw e;
        } catch (Exception e) {
            providerEventBuilder.errorDescription(e.getMessage());
            throw e;
        } finally {
            providerEventBuilder.apiClientClass(apiClient.getClass().getName());
            if (response != null) providerEventBuilder.rawDetail(response.getRawDetail());
            providerEventRepository.save(providerEventBuilder.build());
        }
        return Pair.of(response.getPojo(), providerEventBuilder.build());
    }

    private boolean shouldFetchRefreshTokenAndRetry(
            GatewayGenericApiException e, GatewayClientConnectionSetting connectionSetting, boolean retryEnabled) {
        return connectionSetting.getRefreshTokenEnabled() && e.getErrorCode() == ErrorCode.FORBIDDEN && retryEnabled;
    }
}
