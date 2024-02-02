package com.ayushmaanbhav.gatewayProvider.service;

import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.CreatePaymentRequest;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.PaymentResponse;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayClientConnectionSetting;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayGenericResponse;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderEvent;
import com.ayushmaanbhav.gatewayProvider.exception.GatewayGenericApiException;
import com.ayushmaanbhav.gatewayProvider.testsetup.event.GatewayProviderEventDataSetup;
import com.ayushmaanbhav.gatewayProvider.testsetup.gatewayConnection.GatewayClientConnectionSettingDataSetup;
import com.ayushmaanbhav.gatewayProvider.testsetup.paymentDto.CreatePaymentRequestDataSetup;
import com.ayushmaanbhav.gatewayProvider.testsetup.paymentDto.PaymentResponseDataSetup;
import com.ayushmaanbhav.gatewayProviderApiClient.ApiClient;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.client.CreatePaymentClient;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class GatewayProviderApiCallerServiceTest extends AbstractServiceTest {
    @Mock
    GatewayProviderRefreshTokenApiCallerService refreshTokenService;
    @Autowired
    GatewayProviderApiCallerService apiCallerService;

    private static final String REFRESH_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTUsInVzZXJuYW";

    @Before
    public void setup() {
        ReflectionTestUtils.setField(apiCallerService, "refreshTokenService", refreshTokenService);
    }

    @Test
    public void testCallHappy() throws ApiException {
        GatewayClientConnectionSetting clientConnectionSetting = GatewayClientConnectionSettingDataSetup
                .getGatewayClientConnectionSetting();
        CreatePaymentRequest createPaymentRequest = CreatePaymentRequestDataSetup.getCreatePaymentRequest();
        PaymentResponse paymentResponse = PaymentResponseDataSetup.getPaymentResponse(PaymentStatus.ACTIVE);
        GatewayProviderEvent event = GatewayProviderEventDataSetup.getGatewayProviderEvent();

        ApiClient<CreatePaymentRequest, PaymentResponse> apiClient = mock(CreatePaymentClient.class);
        GatewayProviderEvent.GatewayProviderEventBuilder providerEventBuilder =
                mock(GatewayProviderEvent.GatewayProviderEventBuilder.class);

        doReturn(GatewayGenericResponse.<PaymentResponse>builder()
                .pojo(paymentResponse)
                .rawDetail(event.getRawDetail())
                .build()).when(apiClient).call(any(), any());

        doReturn(event).when(providerEventBuilder).build();

        Pair<PaymentResponse, GatewayProviderEvent> responsePair =
                apiCallerService.call(apiClient, clientConnectionSetting, createPaymentRequest);
        assertNotNull(responsePair);
        verify(apiClient, times(1)).call(any(),any());
    }

    @Test
    public void testCallSadGatewayGenericApiException() throws ApiException {
        GatewayClientConnectionSetting clientConnectionSetting = GatewayClientConnectionSettingDataSetup
                .getGatewayClientConnectionSetting();
        CreatePaymentRequest createPaymentRequest = CreatePaymentRequestDataSetup.getCreatePaymentRequest();
        GatewayProviderEvent event = GatewayProviderEventDataSetup.getGatewayProviderEvent();

        ApiClient<CreatePaymentRequest, PaymentResponse> apiClient = mock(CreatePaymentClient.class);
        GatewayProviderEvent.GatewayProviderEventBuilder providerEventBuilder =
                mock(GatewayProviderEvent.GatewayProviderEventBuilder.class);
        doThrow(new GatewayGenericApiException(
                "test error message", ErrorCode.FORBIDDEN, event.getRawDetail())).when(apiClient)
                .call(any(), any());
        doReturn(REFRESH_TOKEN).when(refreshTokenService).refresh(any(), any());
        doReturn(event).when(providerEventBuilder).build();

        try {
            apiCallerService.call(apiClient, clientConnectionSetting, createPaymentRequest);
            fail("Should have thrown exception");
        } catch (GatewayGenericApiException e) {
            assertEquals(ErrorCode.FORBIDDEN, e.getErrorCode());
        }
        verify(apiClient, times(2)).call(any(),any());
        verify(refreshTokenService, times(1)).refresh(any(), any());
    }

    @Test
    public void testCallSadApiException() throws ApiException {
        GatewayClientConnectionSetting clientConnectionSetting = GatewayClientConnectionSettingDataSetup
                .getGatewayClientConnectionSetting();
        CreatePaymentRequest createPaymentRequest = CreatePaymentRequestDataSetup.getCreatePaymentRequest();
        GatewayProviderEvent event = GatewayProviderEventDataSetup.getGatewayProviderEvent();

        ApiClient<CreatePaymentRequest, PaymentResponse> apiClient = mock(CreatePaymentClient.class);
        GatewayProviderEvent.GatewayProviderEventBuilder providerEventBuilder =
                mock(GatewayProviderEvent.GatewayProviderEventBuilder.class);

        doThrow(new ApiException("Test API Exception", ErrorCode.INTERNAL_SERVER_ERROR)).when(apiClient)
                .call(any(), any());
        doReturn(event).when(providerEventBuilder).build();

        try {
            apiCallerService.call(apiClient, clientConnectionSetting, createPaymentRequest);
            fail("Should have thrown exception");
        } catch (ApiException e) {
            assertEquals(ErrorCode.INTERNAL_SERVER_ERROR, e.getErrorCode());
        }
        verify(apiClient, times(1)).call(any(),any());
    }

}
