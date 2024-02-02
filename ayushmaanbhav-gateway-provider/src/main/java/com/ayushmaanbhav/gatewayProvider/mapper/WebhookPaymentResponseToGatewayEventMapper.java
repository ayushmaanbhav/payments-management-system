package com.ayushmaanbhav.gatewayProvider.mapper;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.mapper.OneWayMapper;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.PaymentResponse;
import com.ayushmaanbhav.gatewayProvider.api.webhook.WebhookRequest;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderEvent;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebhookPaymentResponseToGatewayEventMapper
        implements OneWayMapper<WebhookRequest<PaymentResponse>, GatewayProviderEvent> {

    @Override
    public @NonNull GatewayProviderEvent forward(@NonNull WebhookRequest<PaymentResponse> request)
            throws ApiException {
        var paymentResponse = request.getData();
        return GatewayProviderEvent.builder()
                .orderId(paymentResponse.getOrderId())
                .providerOrderId(paymentResponse.getProviderOrderId())
                .providerSessionId(paymentResponse.getProviderSessionId())
                .providerTransactionId(paymentResponse.getProviderTransactionId())
                .providerStatus(paymentResponse.getProviderStatus())
                .apiClientClass(request.getProvider().name())
                .rawDetail(request.getApiEventDetail())
                .errorCode(paymentResponse.getProviderErrorCode())
                .errorDescription(paymentResponse.getProviderErrorDescription())
                .build();
    }
}
