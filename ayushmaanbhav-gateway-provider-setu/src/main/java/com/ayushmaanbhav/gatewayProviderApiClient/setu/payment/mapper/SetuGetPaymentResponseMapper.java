package com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.mapper;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.mapper.OneWayMapper;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.PaymentResponse;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto.GenericSetuPaymentResponse;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto.SetuGetPaymentResponseData;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SetuGetPaymentResponseMapper
        implements OneWayMapper<GenericSetuPaymentResponse<SetuGetPaymentResponseData>, PaymentResponse> {
    SetuPaymentStatusMapper paymentStatusMapper;

    @Override
    public @NonNull PaymentResponse forward(
            @NonNull GenericSetuPaymentResponse<SetuGetPaymentResponseData> input) throws ApiException {
        return PaymentResponse.builder()
                .orderId(input.getData().getOrderId())
                .providerOrderId(input.getData().getProviderOrderId())
                .status(paymentStatusMapper.forward(input.getData().getStatus()))
                .paymentWebUrl(input.getData().getPaymentLink().getShortUrl())
                .paymentDeepLink(input.getData().getPaymentLink().getUpiLink())
                .providerStatus(input.getData().getStatus().name())
                .build();
    }
}
