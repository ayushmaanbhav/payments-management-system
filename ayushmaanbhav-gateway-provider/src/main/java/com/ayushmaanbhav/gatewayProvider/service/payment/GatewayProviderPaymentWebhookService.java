package com.ayushmaanbhav.gatewayProvider.service.payment;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.PaymentResponse;
import com.ayushmaanbhav.gatewayProvider.api.webhook.WebhookApi;
import com.ayushmaanbhav.gatewayProvider.api.webhook.WebhookRequest;
import com.ayushmaanbhav.gatewayProvider.embedded.ApiEventDetail;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderEvent;
import com.ayushmaanbhav.gatewayProvider.entity.repository.GatewayProviderEventRepository;
import com.ayushmaanbhav.gatewayProvider.mapper.WebhookPaymentResponseToGatewayEventMapper;
import com.ayushmaanbhav.payment.api.payment.PaymentWebhookApi;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class GatewayProviderPaymentWebhookService implements WebhookApi<PaymentResponse, Void> {
    GatewayProviderEventRepository providerEventRepository;
    WebhookPaymentResponseToGatewayEventMapper gatewayEventMapper;
    PaymentWebhookApi paymentWebhookApi;

    @Override
    public @NonNull Void process(@NonNull WebhookRequest<PaymentResponse> request) throws ApiException {
        GatewayProviderEvent providerEvent = gatewayEventMapper.forward(request);
        try {
            paymentWebhookApi.processPaymentWebhook(request.getData());
        } finally {
            providerEventRepository.save(providerEvent);
        }
        return null;
    }

    @Override
    public void saveEventApiDetail(@NonNull ApiEventDetail apiDetail) {
        GatewayProviderEvent providerEvent = GatewayProviderEvent.builder()
                .rawDetail(apiDetail)
                .build();
        providerEventRepository.save(providerEvent);
    }
}
