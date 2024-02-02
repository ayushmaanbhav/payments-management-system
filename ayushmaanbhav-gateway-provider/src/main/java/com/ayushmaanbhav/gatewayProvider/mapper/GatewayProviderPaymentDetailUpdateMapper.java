package com.ayushmaanbhav.gatewayProvider.mapper;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.mapper.OneWayMapper;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.PaymentResponse;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderPaymentDetail;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GatewayProviderPaymentDetailUpdateMapper
        implements OneWayMapper<Pair<GatewayProviderPaymentDetail, PaymentResponse>, GatewayProviderPaymentDetail> {

    @Override
    public @NonNull GatewayProviderPaymentDetail forward(
            @NonNull Pair<GatewayProviderPaymentDetail, PaymentResponse> inputPair
    ) throws ApiException {
        GatewayProviderPaymentDetail providerPaymentDetail = inputPair.getFirst();
        PaymentResponse paymentResponse =  inputPair.getSecond();
        providerPaymentDetail.setProviderStatus(paymentResponse.getProviderStatus());
        providerPaymentDetail.setProviderSessionId(paymentResponse.getProviderSessionId());
        providerPaymentDetail.setProviderTransactionId(paymentResponse.getProviderTransactionId());
        providerPaymentDetail.setProviderPaymentMethod(paymentResponse.getProviderPaymentMethod());
        providerPaymentDetail.setProviderPaidDate(paymentResponse.getProviderPaidDate());
        providerPaymentDetail.setProviderErrorCode(paymentResponse.getProviderErrorCode());
        providerPaymentDetail.setProviderErrorDescription(paymentResponse.getProviderErrorDescription());
        return providerPaymentDetail;
    }
}
