package com.ayushmaanbhav.gatewayProvider.testsetup.gatewayConnection;

import com.ayushmaanbhav.gatewayProvider.dto.GatewayConnectionSettingDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import static com.ayushmaanbhav.gatewayProvider.testsetup.util.TestValues.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GatewayConnectionSettingDtoDataSetup {

    public static GatewayConnectionSettingDto getGatewayConnectionSettingDto() {
        return GatewayConnectionSettingDto.builder()
                .provider(PROVIDER)
                .baseUrl(BASE_URL)
                .key(KEY)
                .secretToken(SECRET)
                .tokenRefreshEnabled(TOKEN_REFRESH)
                .build();
    }
}
