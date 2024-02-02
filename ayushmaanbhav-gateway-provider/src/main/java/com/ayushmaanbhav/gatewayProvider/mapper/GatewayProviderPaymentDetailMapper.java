package com.ayushmaanbhav.gatewayProvider.mapper;

import com.ayushmaanbhav.commons.contstants.PaymentType;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.mapper.OneWayMapper;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.PaymentResponse;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderPaymentDetail;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GatewayProviderPaymentDetailMapper implements OneWayMapper<PaymentResponse, GatewayProviderPaymentDetail> {

    @Override
    public @NonNull GatewayProviderPaymentDetail forward(@NonNull PaymentResponse input) throws ApiException {
        return GatewayProviderPaymentDetail.builder()
                .orderId(input.getOrderId())
                .type(PaymentType.PAYMENT)
                .providerStatus(input.getProviderStatus())
                .providerOrderId(input.getProviderOrderId())
                .paymentWebUrl(input.getPaymentWebUrl())
                .paymentDeepLink(input.getPaymentDeepLink())
                .providerSessionId(input.getProviderSessionId())
                .providerTransactionId(input.getProviderTransactionId())
                .providerPaymentMethod(input.getProviderPaymentMethod())
                .providerPaidDate(input.getProviderPaidDate())
                .providerErrorCode(input.getProviderErrorCode())
                .providerErrorDescription(input.getProviderErrorDescription())
                .build();
    }
}
