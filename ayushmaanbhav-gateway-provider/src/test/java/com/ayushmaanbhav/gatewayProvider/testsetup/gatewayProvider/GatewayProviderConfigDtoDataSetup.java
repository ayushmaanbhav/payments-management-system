package com.ayushmaanbhav.gatewayProvider.testsetup.gatewayProvider;

import com.ayushmaanbhav.gatewayProvider.dto.GatewayProviderConfigDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import static com.ayushmaanbhav.gatewayProvider.testsetup.util.TestValues.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GatewayProviderConfigDtoDataSetup {

    public static GatewayProviderConfigDto getGatewayProviderConfigDto() {
        return GatewayProviderConfigDto.builder()
                .provider(PROVIDER)
                .merchantId(MERCHANT_ID)
                .connectionSettingId(GATEWAY_CONNECTION_EXTERNAL_ID)
                .disabled(CONFIG_DISABLED)
                .build();
    }
}
