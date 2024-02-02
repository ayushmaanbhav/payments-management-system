package com.ayushmaanbhav.payment.mapper;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.mapper.OneWayMapper;
import com.ayushmaanbhav.commons.request.PaymentConfirmWebhookRequest;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentConfirmWebhookRequestMapper implements OneWayMapper<PaymentOrderResponse, PaymentConfirmWebhookRequest> {

    @Override
    public @NonNull PaymentConfirmWebhookRequest forward(@NonNull PaymentOrderResponse input) throws ApiException {
        return PaymentConfirmWebhookRequest.builder()
                .paymentOrderId(input.getPaymentOrderId())
                .status(input.getStatus())
                .transactionId(input.getTransactionId())
                .paidDate(input.getPaidDate())
                .expiredDate(input.getExpiredDate())
                .build();
    }
}
