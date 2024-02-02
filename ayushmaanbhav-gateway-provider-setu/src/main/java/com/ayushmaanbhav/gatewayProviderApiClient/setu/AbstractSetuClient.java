package com.ayushmaanbhav.gatewayProviderApiClient.setu;

import com.ayushmaanbhav.commons.contstants.GatewayProvider;
import com.ayushmaanbhav.gatewayProviderApiClient.ApiClient;
import lombok.NonNull;

public abstract class AbstractSetuClient<Req, Res> implements ApiClient<Req, Res> {

    @Override
    public final @NonNull GatewayProvider getGatewayProvider() {
        return GatewayProvider.SETU;
    }
}
