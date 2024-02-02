package com.ayushmaanbhav.gatewayProvider.testsetup.gatewayConnection;

import com.ayushmaanbhav.gatewayProvider.dto.ConnectionSettingDto;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayConnectionSetting;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import static com.ayushmaanbhav.gatewayProvider.testsetup.util.TestValues.*;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GatewayConnectionSettingDataSetup {

    public static GatewayConnectionSetting getGatewayConnectionSetting() {
        ConnectionSettingDto connectionSettingDto = ConnectionSettingDto.builder()
                .baseUrl(BASE_URL)
                .key(KEY)
                .secretToken(SECRET)
                .build();
        return GatewayConnectionSetting.builder()
                .externalId(GATEWAY_CONNECTION_EXTERNAL_ID)
                .provider(PROVIDER)
                .connectionSetting(connectionSettingDto)
                .tokenRefreshEnabled(TOKEN_REFRESH)
                .retryCount(0)
                .build();
    }

}
