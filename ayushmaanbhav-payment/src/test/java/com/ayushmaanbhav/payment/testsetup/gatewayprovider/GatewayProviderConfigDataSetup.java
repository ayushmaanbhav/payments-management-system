package com.ayushmaanbhav.payment.testsetup.gatewayprovider;

import com.ayushmaanbhav.commons.contstants.GatewayProvider;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayConnectionSetting;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderConfig;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import static com.ayushmaanbhav.payment.util.TestValues.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GatewayProviderConfigDataSetup {


    public static GatewayProviderConfig getGatewayProviderConfig() {
        return GatewayProviderConfig.builder()
                .externalId(PROVIDER_CONFIG_EXTERNAL_ID)
                .provider(PROVIDER)
                .merchantId(MERCHANT_ID)
                .connectionSetting(GatewayConnectionSettingDataSetup.getGatewayConnectionSetting())
                .disabled(CONFIG_DISABLED)
                .build();
    }
}
