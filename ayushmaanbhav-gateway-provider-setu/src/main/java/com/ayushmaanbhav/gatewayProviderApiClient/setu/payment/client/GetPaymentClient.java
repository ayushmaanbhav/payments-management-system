package com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.GetPaymentRequest;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.PaymentResponse;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayClientConnectionSetting;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayGenericResponse;
import com.ayushmaanbhav.gatewayProviderApiClient.genericHttpClient.GatewayClient;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.AbstractSetuClient;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto.GenericSetuPaymentResponse;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto.SetuGetPaymentResponseData;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto.error.SetuErrorResponse;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.mapper.SetuErrorConverter;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.mapper.SetuErrorMessageRetriever;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.mapper.SetuGetPaymentResponseMapper;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;

@Log4j2
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetPaymentClient extends AbstractSetuClient<GetPaymentRequest, PaymentResponse> {
    SetuGetPaymentResponseMapper setuPaymentResponseMapper;
    SetuErrorConverter setuErrorConverter;
    SetuErrorMessageRetriever setuErrorMessageRetriever;
    GatewayClient gatewayClient;

    @Override
    public @NonNull GatewayGenericResponse<PaymentResponse> call(
            @NonNull GatewayClientConnectionSetting connectionSetting, @NonNull GetPaymentRequest request
    ) throws ApiException {
        String baseUrl = connectionSetting.getBaseUrl();
        String refreshToken = connectionSetting.getRefreshToken();
        String merchantId = connectionSetting.getMerchantId();
        HttpHeaders httpHeaders = new HttpHeaders() {{
            add("X-Setu-Product-Instance-ID",  merchantId);
            add("Authorization", "Bearer " + refreshToken);
        }};

        // Need to add error converter
        String providerOrderId = request.getProviderOrderId();
        log.info("Calling SETU Get Payment Client for order with order Id: " + request.getOrderId());
        GatewayGenericResponse<GenericSetuPaymentResponse<SetuGetPaymentResponseData>> genericResponse = gatewayClient
                .makeGatewayRequest(HttpMethod.GET, baseUrl, "payment-links/" + providerOrderId, httpHeaders,
                        new LinkedMultiValueMap<>(), null, new TypeReference<>() {},
                        new TypeReference<SetuErrorResponse>() {}, setuErrorConverter, setuErrorMessageRetriever);
        GenericSetuPaymentResponse<SetuGetPaymentResponseData> setuPaymentResponse = genericResponse.getPojo();
        PaymentResponse paymentResponse = setuPaymentResponseMapper
                .forward(setuPaymentResponse);

        return GatewayGenericResponse.<PaymentResponse>builder()
                .pojo(paymentResponse)
                .rawDetail(genericResponse.getRawDetail())
                .build();
    }
}
