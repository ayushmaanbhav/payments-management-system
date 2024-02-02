package com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayClientConnectionSetting;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayGenericResponse;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayRefreshTokenRequest;
import com.ayushmaanbhav.gatewayProviderApiClient.genericHttpClient.GatewayClient;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.AbstractSetuClient;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto.SetuTokenRequest;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto.SetuTokenResponse;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto.error.SetuErrorResponse;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.mapper.SetuErrorConverter;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.mapper.SetuErrorMessageRetriever;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;

@Log4j2
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshTokenClient extends AbstractSetuClient<GatewayRefreshTokenRequest, String> {
    SetuErrorConverter setuErrorConverter;
    SetuErrorMessageRetriever setuErrorMessageRetriever;
    GatewayClient gatewayClient;

    @Override
    public @NonNull GatewayGenericResponse<String> call(
            @NonNull GatewayClientConnectionSetting connectionSetting, @NonNull GatewayRefreshTokenRequest request
    ) throws ApiException {
        String baseUrl = connectionSetting.getBaseUrl();
        String clientId = connectionSetting.getKey();
        String secretToken = connectionSetting.getSecretToken();

        SetuTokenRequest tokenRequest = SetuTokenRequest.builder()
                .clientId(clientId)
                .secret(secretToken).build();

        log.info("Calling SETU Refresh Token Client");
        GatewayGenericResponse<SetuTokenResponse> response = gatewayClient.makeGatewayRequest(HttpMethod.POST,
                baseUrl, "auth/token", new HttpHeaders(), new LinkedMultiValueMap<>(), tokenRequest,
                new TypeReference<>() {}, new TypeReference<SetuErrorResponse>() {}, setuErrorConverter,
                setuErrorMessageRetriever);
        String token = response.getPojo().getData().getToken();
        return GatewayGenericResponse.<String>builder()
                .pojo(token)
                .rawDetail(response.getRawDetail()).build();
    }
}
