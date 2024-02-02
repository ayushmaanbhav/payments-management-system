package com.ayushmaanbhav.payment.testsetup.gatewayprovider;

import com.ayushmaanbhav.gatewayProvider.entity.GatewayConnectionSetting;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderConfig;
import com.ayushmaanbhav.gatewayProvider.entity.repository.GatewayProviderConfigRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class GatewayProviderConfigSetup {

    GatewayProviderConfig providerConfig;
    @Autowired
    GatewayProviderConfigRepository gatewayProviderConfigRepository;

    @Autowired
    GatewayConnectionSettingSetup gatewayConnectionSettingSetup;

    public GatewayProviderConfig setupGatewayProviderConfig() {
        if(providerConfig != null) {
            if(gatewayProviderConfigRepository.selectByExternalId(providerConfig.getExternalId()) != null) {
                return providerConfig;
            }
        }
        GatewayProviderConfig gatewayProviderConfig = GatewayProviderConfigDataSetup.getGatewayProviderConfig();
        GatewayConnectionSetting gatewayConnectionSetting = gatewayConnectionSettingSetup
                .setupGatewayConnectionSetting();
        gatewayProviderConfig.setConnectionSetting(gatewayConnectionSetting);
        gatewayProviderConfigRepository.save(gatewayProviderConfig);
        return providerConfig = gatewayProviderConfig;
    }

}
