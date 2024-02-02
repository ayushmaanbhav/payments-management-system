package com.ayushmaanbhav.payment.testsetup.gatewayprovider;

import com.ayushmaanbhav.gatewayProvider.entity.GatewayConnectionSetting;
import com.ayushmaanbhav.gatewayProvider.entity.repository.GatewayConnectionSettingRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class GatewayConnectionSettingSetup {

    GatewayConnectionSetting connectionSetting;
    @Autowired
    GatewayConnectionSettingRepository gatewayConnectionSettingRepository;

    public GatewayConnectionSetting setupGatewayConnectionSetting() {
        if(connectionSetting != null) {
            if(gatewayConnectionSettingRepository.selectByExternalId(connectionSetting.getExternalId()) != null) {
                return connectionSetting;
            }
        }
        GatewayConnectionSetting gatewayConnectionSetting = GatewayConnectionSettingDataSetup
                .getGatewayConnectionSetting();
        gatewayConnectionSettingRepository.save(gatewayConnectionSetting);
        return connectionSetting = gatewayConnectionSetting;
    }

}
