package com.ayushmaanbhav.gatewayProvider.testsetup.gatewayConnection;

import com.ayushmaanbhav.gatewayProvider.dto.GatewayClientConnectionSetting;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import static com.ayushmaanbhav.gatewayProvider.testsetup.util.TestValues.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GatewayClientConnectionSettingDataSetup {

    public static GatewayClientConnectionSetting getGatewayClientConnectionSetting() {
        return GatewayClientConnectionSetting.builder()
                .provider(PROVIDER)
                .merchantId(MERCHANT_ID)
                .baseUrl(BASE_URL)
                .key(KEY)
                .secretToken(SECRET)
                .refreshTokenEnabled(TOKEN_REFRESH)
                .build();
    }
}
