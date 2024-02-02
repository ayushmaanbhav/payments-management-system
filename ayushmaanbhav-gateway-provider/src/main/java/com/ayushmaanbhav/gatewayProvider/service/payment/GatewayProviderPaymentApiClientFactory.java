package com.ayushmaanbhav.gatewayProvider.service.payment;

import com.ayushmaanbhav.commons.contstants.GatewayProvider;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.CreatePaymentRequest;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.GetPaymentRequest;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.PaymentResponse;
import com.ayushmaanbhav.gatewayProviderApiClient.ApiClient;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GatewayProviderPaymentApiClientFactory {
    Map<GatewayProvider, ApiClient<CreatePaymentRequest, PaymentResponse>> createPaymentApiClients;
    Map<GatewayProvider, ApiClient<GetPaymentRequest, PaymentResponse>> getPaymentApiClients;

    @Autowired
    public GatewayProviderPaymentApiClientFactory(
            @NonNull List<ApiClient<CreatePaymentRequest, PaymentResponse>> createPaymentApiClients,
            @NonNull List<ApiClient<GetPaymentRequest, PaymentResponse>> getPaymentApiClients
    ) {
        this.createPaymentApiClients = new HashMap<>();
        for (var apiClient : createPaymentApiClients) {
            this.createPaymentApiClients.put(apiClient.getGatewayProvider(), apiClient);
        }

        this.getPaymentApiClients = new HashMap<>();
        for (var apiClient : getPaymentApiClients) {
            this.getPaymentApiClients.put(apiClient.getGatewayProvider(), apiClient);
        }
    }

    public @NonNull ApiClient<CreatePaymentRequest, PaymentResponse> getCreatePaymentApiClient(@NonNull GatewayProvider provider) {
        return this.createPaymentApiClients.get(provider);
    }

    public @NonNull ApiClient<GetPaymentRequest, PaymentResponse> getGetPaymentApiClient(@NonNull GatewayProvider provider) {
        return this.getPaymentApiClients.get(provider);
    }
}
