package com.ayushmaanbhav.payment.mapper;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import com.ayushmaanbhav.commons.mapper.OneWayMapper;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.GetPaymentRequest;
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
public class GatewayProviderGetPaymentRequestMapper implements OneWayMapper<PaymentOrder, GetPaymentRequest> {
    @Override
    public @NonNull GetPaymentRequest forward(@NonNull PaymentOrder input) throws ApiException {
        PaymentOrderLineItem onlyItem = input.getLineItems().stream().findFirst().orElseThrow(() ->
                new ApiException("no line item found inside payment order", ErrorCode.INTERNAL_SERVER_ERROR));
        return GetPaymentRequest.builder()
                .orderId(onlyItem.getExternalId())
                .gatewayProviderConfigId(onlyItem.getGatewayProviderConfig().getExternalId())
                .providerOrderId(onlyItem.getGatewayProviderPaymentDetail().getProviderOrderId())
                .build();
    }
}
