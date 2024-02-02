package com.ayushmaanbhav.gatewayProvider.service;

import com.ayushmaanbhav.commons.contstants.GatewayProvider;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayConnectionSettingDto;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayConnectionSetting;
import com.ayushmaanbhav.gatewayProvider.entity.repository.GatewayConnectionSettingRepository;
import com.ayushmaanbhav.gatewayProvider.mapper.GatewayClientTokenRefreshConnectionSettingMapper;
import com.ayushmaanbhav.gatewayProvider.testsetup.gatewayConnection.GatewayConnectionSettingDtoDataSetup;
import com.ayushmaanbhav.gatewayProvider.testsetup.gatewayConnection.GatewayConnectionSettingSetup;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@FieldDefaults(level =  AccessLevel.PRIVATE)
public class GatewayConnectionSettingServiceTest extends AbstractServiceTest {

    @Autowired
    GatewayConnectionSettingService gatewayConnectionSettingService;
    @Autowired
    GatewayConnectionSettingRepository gatewayConnectionSettingRepository;
    @Autowired
    GatewayConnectionSettingSetup gatewayConnectionSettingSetup;
    @Mock
    Map<GatewayProvider, Long> refreshTokenExpiryConfig;
    @Mock
    GatewayProviderRefreshTokenApiCallerService refreshTokenApiCallerService;
    @Mock
    GatewayProviderApiCallerService gatewayProviderApiCallerService;
    @Mock
    GatewayClientTokenRefreshConnectionSettingMapper tokenRefreshConnectionSettingMapper;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(gatewayConnectionSettingService,
                "refreshTokenExpiryConfig", refreshTokenExpiryConfig);
        ReflectionTestUtils.setField(gatewayConnectionSettingService,
                "refreshTokenApiCallerService", refreshTokenApiCallerService);
        ReflectionTestUtils.setField(gatewayConnectionSettingService,
                "gatewayProviderApiCallerService", gatewayProviderApiCallerService);
        ReflectionTestUtils.setField(gatewayConnectionSettingService,
                "tokenRefreshConnectionSettingMapper", tokenRefreshConnectionSettingMapper);
    }

    private static final String NON_EXISTENT_EXTERNAL_ID = "eg8a0289a62146ed9a1ab47660e2c641";
    private static final String REFRESH_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTUsInVzZXJuYW";

    @Test
    public void testSave() throws ApiException {
        GatewayConnectionSettingDto settingDto = GatewayConnectionSettingDtoDataSetup.getGatewayConnectionSettingDto();
        String externalId = gatewayConnectionSettingService.save(settingDto);
        GatewayConnectionSetting setting = gatewayConnectionSettingRepository.selectByExternalId(externalId);
        assertNotNull(setting);
        assertEquals(settingDto.getProvider(), setting.getProvider());
        assertEquals(settingDto.getTokenRefreshEnabled(), setting.getTokenRefreshEnabled());
        assertEquals(settingDto.getBaseUrl(), setting.getConnectionSetting().getBaseUrl());
        assertEquals(settingDto.getKey(), setting.getConnectionSetting().getKey());
        assertEquals(settingDto.getSecretToken(), setting.getConnectionSetting().getSecretToken());
    }

    @Test
    public void testGetByExternalId() throws ApiException {
        GatewayConnectionSettingDto settingDto = GatewayConnectionSettingDtoDataSetup.getGatewayConnectionSettingDto();
        String externalId = gatewayConnectionSettingService.save(settingDto);
        GatewayConnectionSetting inDB = gatewayConnectionSettingRepository.selectByExternalId(externalId);
        GatewayConnectionSetting setting = gatewayConnectionSettingService.getByExternalId(externalId);
        assertNotNull(setting);
        assertNotNull(setting.getConnectionSetting());
        assertEquals(inDB.getProvider(), setting.getProvider());
        assertEquals(inDB.getTokenRefreshEnabled(), setting.getTokenRefreshEnabled());
        assertEquals(inDB.getConnectionSetting().getBaseUrl(), setting.getConnectionSetting().getBaseUrl());
        assertEquals(inDB.getConnectionSetting().getKey(), setting.getConnectionSetting().getKey());
        assertEquals(inDB.getConnectionSetting().getSecretToken(), setting.getConnectionSetting().getSecretToken());

        try {
            gatewayConnectionSettingService.getByExternalId(NON_EXISTENT_EXTERNAL_ID);
            fail("Should throw exception");
        } catch (ApiException e) {
            assertEquals(ErrorCode.NOT_FOUND, e.getErrorCode());
        }
    }

    @Test
    public void testRefreshTokenHappy() throws ApiException {
        GatewayConnectionSettingDto settingDto = GatewayConnectionSettingDtoDataSetup.getGatewayConnectionSettingDto();
        String externalId = gatewayConnectionSettingService.save(settingDto);
        GatewayConnectionSetting setting = gatewayConnectionSettingService.getByExternalId(externalId);
        doReturn(150L).when(refreshTokenExpiryConfig).get(any());
        doReturn(REFRESH_TOKEN).when(refreshTokenApiCallerService).refresh(any(), any());

        assertNull(setting.getConnectionSetting().getRefreshToken());
        assertNull(setting.getLastTokenRefreshDate());

        gatewayConnectionSettingService.refreshToken(externalId);

        setting = gatewayConnectionSettingService.getByExternalId(externalId);
        assertNotNull(setting.getConnectionSetting().getRefreshToken());
        assertNotNull(setting.getLastTokenRefreshDate());
        assertNotNull(setting.getRetryCount());
        assertNotNull(setting.getLastTokenRefreshDate());
        assertEquals(0, (int) setting.getRetryCount());

        verify(refreshTokenExpiryConfig, times(1)).get(any());
        verify(refreshTokenApiCallerService, times(1)).refresh(any(),any());
        verify(tokenRefreshConnectionSettingMapper, times(1)).forward(any());
    }

    @Test
    public void testRefreshTokenSad() throws ApiException {
        GatewayConnectionSettingDto settingDto = GatewayConnectionSettingDtoDataSetup.getGatewayConnectionSettingDto();
        String externalId = gatewayConnectionSettingService.save(settingDto);
        GatewayConnectionSetting setting = gatewayConnectionSettingService.getByExternalId(externalId);
        doReturn(150L).when(refreshTokenExpiryConfig).get(any());
        doThrow(new ApiException("Test Error Message", ErrorCode.INTERNAL_SERVER_ERROR))
                .when(refreshTokenApiCallerService).refresh(any(), any());

        assertNull(setting.getConnectionSetting().getRefreshToken());
        assertNull(setting.getLastTokenRefreshDate());

        try {
            gatewayConnectionSettingService.refreshToken(externalId);
            fail("Should have thrown exception");
        } catch (ApiException e) {
            assertEquals(ErrorCode.INTERNAL_SERVER_ERROR, e.getErrorCode());
        }

        setting = gatewayConnectionSettingService.getByExternalId(externalId);
        assertEquals((int)setting.getRetryCount(), 1);
    }

}
