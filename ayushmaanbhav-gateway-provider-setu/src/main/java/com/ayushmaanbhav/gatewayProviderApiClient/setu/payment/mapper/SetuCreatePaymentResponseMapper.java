package com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.mapper;

import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.mapper.OneWayMapper;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.PaymentResponse;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.constant.SetuPaymentStatus;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto.GenericSetuPaymentResponse;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto.SetuCreatePaymentResponseData;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SetuCreatePaymentResponseMapper
        implements OneWayMapper<Pair<GenericSetuPaymentResponse<SetuCreatePaymentResponseData>, String>, PaymentResponse> {

    @Override
    public @NonNull PaymentResponse forward(
            @NonNull Pair<GenericSetuPaymentResponse<SetuCreatePaymentResponseData>, String> inputPair) throws ApiException {
        var input = inputPair.getFirst();
        var orderId = inputPair.getSecond();
        return PaymentResponse.builder()
                .orderId(orderId)
                .providerOrderId(input.getData().getProviderOrderId())
                .paymentWebUrl(input.getData().getPaymentLink().getShortUrl())
                .paymentDeepLink(input.getData().getPaymentLink().getUpiLink())
                .status(PaymentStatus.ACTIVE) // status not returned by setu in create api
                .providerStatus(SetuPaymentStatus.BILL_CREATED.name()) // status not returned by setu in create api
                .build();
    }
}
