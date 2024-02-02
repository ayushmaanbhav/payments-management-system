package com.ayushmaanbhav.payment.mapper;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import com.ayushmaanbhav.commons.mapper.OneWayMapper;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderResponse;
import com.ayushmaanbhav.payment.entity.PaymentOrder;
import com.ayushmaanbhav.payment.entity.PaymentOrderLineItem;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentOrderResponseMapper implements OneWayMapper<PaymentOrder, PaymentOrderResponse> {

    @Override
    public @NonNull PaymentOrderResponse forward(@NonNull PaymentOrder input) throws ApiException {
        PaymentOrderLineItem onlyItem = input.getLineItems().stream().findFirst().orElseThrow(() ->
                new ApiException("no line item found inside payment order", ErrorCode.INTERNAL_SERVER_ERROR));
        return PaymentOrderResponse.builder()
                .paymentOrderId(input.getExternalId())
                .status(input.getStatus())
                .paymentWebUrl(onlyItem.getGatewayProviderPaymentDetail().getPaymentWebUrl())
                .paymentDeepLink(onlyItem.getGatewayProviderPaymentDetail().getPaymentDeepLink())
                .transactionId(onlyItem.getExternalId())
                .paidDate(onlyItem.getPaidDate())
                .expiredDate(onlyItem.getExpiredDate())
                .build();
    }
}
