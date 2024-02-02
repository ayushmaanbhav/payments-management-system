package com.ayushmaanbhav.gatewayProvider.service;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayProviderConfigDto;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayConnectionSetting;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderConfig;
import com.ayushmaanbhav.gatewayProvider.entity.repository.GatewayProviderConfigRepository;
import com.ayushmaanbhav.gatewayProvider.mapper.GatewayProviderConfigMapper;
import com.ayushmaanbhav.gatewayProvider.testsetup.gatewayConnection.GatewayConnectionSettingSetup;
import com.ayushmaanbhav.gatewayProvider.testsetup.gatewayProvider.GatewayProviderConfigDataSetup;
import com.ayushmaanbhav.gatewayProvider.testsetup.gatewayProvider.GatewayProviderConfigDtoDataSetup;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GatewayProviderConfigServiceTest extends AbstractServiceTest {

    @Autowired
    GatewayConnectionSettingSetup gatewayConnectionSettingSetup;
    @Autowired
    GatewayProviderConfigRepository gatewayProviderConfigRepository;
    @Autowired
    GatewayProviderConfigService gatewayProviderConfigService;
    @Mock
    GatewayConnectionSettingService gatewayConnectionSettingService;
    @Mock
    GatewayProviderConfigMapper gatewayProviderConfigMapper;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(gatewayProviderConfigService,
                "gatewayConnectionSettingService", gatewayConnectionSettingService);
        ReflectionTestUtils.setField(gatewayProviderConfigService,
                "gatewayProviderConfigMapper", gatewayProviderConfigMapper);
    }

    private static final String NON_EXISTENT_EXTERNAL_ID = "eg8a0289a62146ed9a1ab47660e2c641";

    @Test
    public void testSave() throws ApiException {
        GatewayConnectionSetting setting = gatewayConnectionSettingSetup.setupGatewayConnectionSetting();
        GatewayProviderConfigDto configDto = GatewayProviderConfigDtoDataSetup.getGatewayProviderConfigDto();
        GatewayProviderConfig providerConfig = GatewayProviderConfigDataSetup.getGatewayProviderConfig();
        doReturn(setting).when(gatewayConnectionSettingService).getByExternalId(any());
        doReturn(providerConfig).when(gatewayProviderConfigMapper).forward(any());

        String externalId = gatewayProviderConfigService.save(configDto);

        GatewayProviderConfig inDB = gatewayProviderConfigRepository.selectByExternalId(externalId);
        assertNotNull(inDB);
        assertNotNull(inDB.getConnectionSetting());
        assertEquals(providerConfig.getProvider(), inDB.getProvider());
        assertEquals(providerConfig.getMerchantId(), inDB.getMerchantId());
    }

    @Test
    public void tetGet() throws ApiException {
        GatewayConnectionSetting setting = gatewayConnectionSettingSetup.setupGatewayConnectionSetting();
        GatewayProviderConfigDto configDto = GatewayProviderConfigDtoDataSetup.getGatewayProviderConfigDto();
        GatewayProviderConfig providerConfig = GatewayProviderConfigDataSetup.getGatewayProviderConfig();
        doReturn(setting).when(gatewayConnectionSettingService).getByExternalId(any());
        doReturn(providerConfig).when(gatewayProviderConfigMapper).forward(any());
        String externalId = gatewayProviderConfigService.save(configDto);

        GatewayProviderConfig inDB = gatewayProviderConfigService.get(externalId);
        assertNotNull(inDB);
        assertNotNull(inDB.getConnectionSetting());
        assertEquals(providerConfig.getProvider(), inDB.getProvider());
        assertEquals(providerConfig.getMerchantId(), inDB.getMerchantId());

        try {
            gatewayProviderConfigService.get(NON_EXISTENT_EXTERNAL_ID);
            fail("Should have thrown exception");
        } catch (ApiException e) {
            assertEquals(ErrorCode.NOT_FOUND, e.getErrorCode());
        }
    }
}
