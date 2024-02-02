package com.ayushmaanbhav.payment.mapper;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import com.ayushmaanbhav.commons.mapper.OneWayMapper;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.CreatePaymentRequest;
import com.ayushmaanbhav.payment.entity.PaymentOrder;
import com.ayushmaanbhav.payment.entity.PaymentOrderLineItem;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GatewayProviderCreatePaymentRequestMapper
        implements OneWayMapper<Pair<PaymentOrder, String>, CreatePaymentRequest> {
    Integer linkExpiryTimeSecond;

    @Autowired
    public GatewayProviderCreatePaymentRequestMapper(
            @NonNull @Value("${ayushmaanbhav.gateway.provider.payment.link.expiry.seconds}") Integer linkExpiryTimeSecond
    ) {
        if (linkExpiryTimeSecond <= 0) {
            throw new IllegalArgumentException("linkExpiryTimeSecond must be positive");
        }
        this.linkExpiryTimeSecond = linkExpiryTimeSecond;
    }

    @Override
    public @NonNull CreatePaymentRequest forward(@NonNull Pair<PaymentOrder, String> input) throws ApiException {
        var paymentOrder = input.getFirst();
        var customerPhone = input.getSecond();
        PaymentOrderLineItem onlyItem = paymentOrder.getLineItems().stream().findFirst().orElseThrow(() ->
                new ApiException("no line item found inside payment order", ErrorCode.INTERNAL_SERVER_ERROR));
        return CreatePaymentRequest.builder()
                .orderId(onlyItem.getExternalId())
                .gatewayProviderConfigId(onlyItem.getGatewayProviderConfig().getExternalId())
                .normalisedAmount(onlyItem.getNormalisedAmount())
                .currency(onlyItem.getCurrency())
                .customerPhone(customerPhone)
                .params(new HashMap<>())
                .expiresInSecond(this.linkExpiryTimeSecond)
                .build();
    }
}
