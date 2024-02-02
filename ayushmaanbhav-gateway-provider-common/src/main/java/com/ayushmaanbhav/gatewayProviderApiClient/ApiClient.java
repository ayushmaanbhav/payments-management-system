package com.ayushmaanbhav.gatewayProviderApiClient;

import com.ayushmaanbhav.commons.contstants.GatewayProvider;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayClientConnectionSetting;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayGenericResponse;
import lombok.NonNull;

public interface ApiClient<Req, Res> {

    @NonNull GatewayGenericResponse<Res> call(
            @NonNull GatewayClientConnectionSetting connectionSetting, @NonNull Req request
    ) throws ApiException;

    @NonNull GatewayProvider getGatewayProvider();
}
