package com.ayushmaanbhav.gatewayProvider.service.payment;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.gatewayProvider.api.payment.GatewayProviderPaymentApi;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.CreatePaymentRequest;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.GetPaymentRequest;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.PaymentResponse;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayClientConnectionSetting;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderConfig;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderEvent;
import com.ayushmaanbhav.gatewayProvider.entity.repository.GatewayProviderConfigRepository;
import com.ayushmaanbhav.gatewayProvider.entity.repository.GatewayProviderEventRepository;
import com.ayushmaanbhav.gatewayProvider.mapper.GatewayClientConnectionSettingMapper;
import com.ayushmaanbhav.gatewayProvider.service.GatewayProviderApiCallerService;
import com.ayushmaanbhav.gatewayProviderApiClient.ApiClient;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class GatewayProviderPaymentService implements GatewayProviderPaymentApi {
    GatewayProviderPaymentApiClientFactory providerFactory;
    GatewayProviderApiCallerService providerApiCallerService;
    GatewayProviderConfigRepository providerConfigRepository;
    GatewayProviderEventRepository providerEventRepository;
    GatewayClientConnectionSettingMapper connectionSettingMapper;

    @Override
    public @NonNull PaymentResponse create(@NonNull CreatePaymentRequest request) throws ApiException {
        GatewayProviderConfig providerConfig = providerConfigRepository
                .selectByExternalId(request.getGatewayProviderConfigId());
        ApiClient<CreatePaymentRequest, PaymentResponse> apiClient = providerFactory
                .getCreatePaymentApiClient(providerConfig.getProvider());
        GatewayClientConnectionSetting connectionSetting = connectionSettingMapper.forward(providerConfig);
        Pair<PaymentResponse, GatewayProviderEvent> responsePair = providerApiCallerService
                .call(apiClient, connectionSetting, request);
        addPaymentParams(responsePair.getFirst(), responsePair.getSecond());
        return responsePair.getFirst();
    }

    @Override
    public @NonNull PaymentResponse get(@NonNull GetPaymentRequest request) throws ApiException {
        GatewayProviderConfig providerConfig = providerConfigRepository
                .selectByExternalId(request.getGatewayProviderConfigId());
        ApiClient<GetPaymentRequest, PaymentResponse> apiClient = providerFactory
                .getGetPaymentApiClient(providerConfig.getProvider());
        GatewayClientConnectionSetting connectionSetting = connectionSettingMapper.forward(providerConfig);
        Pair<PaymentResponse, GatewayProviderEvent> responsePair = providerApiCallerService
                .call(apiClient, connectionSetting, request);
        addPaymentParams(responsePair.getFirst(), responsePair.getSecond());
        return responsePair.getFirst();
    }

    private void addPaymentParams(PaymentResponse response, GatewayProviderEvent providerEvent) {
        providerEvent.setOrderId(response.getOrderId());
        providerEvent.setProviderOrderId(response.getProviderOrderId());
        providerEvent.setProviderSessionId(response.getProviderSessionId());
        providerEvent.setProviderTransactionId(response.getProviderTransactionId());
        providerEvent.setProviderStatus(response.getProviderStatus());
        providerEventRepository.save(providerEvent);
    }
}
