package com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.mapper;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.mapper.OneWayMapper;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.PaymentResponse;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.constant.SetuPaymentMethod;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto.WebhookRequest;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebhookPaymentResponseMapper implements OneWayMapper<WebhookRequest.Event, PaymentResponse> {
    SetuPaymentStatusMapper paymentStatusMapper;

    @Override
    public @NonNull PaymentResponse forward(@NonNull WebhookRequest.Event inputEvent)
            throws ApiException {
        var input = inputEvent.getData();
        return PaymentResponse.builder()
                .orderId(input.getBillerBillID())
                .providerOrderId(input.getPlatformBillID())
                .status(paymentStatusMapper.forward(input.getStatus()))
                .providerTransactionId(input.getPlatformBillID())
                .providerPaymentMethod(SetuPaymentMethod.UPI.name())
                .providerPaidDate(inputEvent.getTimeStamp())
                .providerStatus(input.getStatus().name())
                .normalisedPaidAmount(input.getAmountPaid().getValue())
                .build();
    }
}
