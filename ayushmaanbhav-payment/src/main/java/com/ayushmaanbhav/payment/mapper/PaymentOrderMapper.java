package com.ayushmaanbhav.payment.mapper;

import com.ayushmaanbhav.commons.contstants.PaymentOrderType;
import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.mapper.OneWayMapper;
import com.ayushmaanbhav.commons.util.LUUID;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderRequest;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderRequestLineItem;
import com.ayushmaanbhav.payment.entity.PaymentOrder;
import com.ayushmaanbhav.payment.entity.PaymentOrderLineItem;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentOrderMapper implements OneWayMapper<PaymentOrderRequest, PaymentOrder> {
    PaymentOrderLineItemMapper lineItemMapper;

    @Override
    public @NonNull PaymentOrder forward(@NonNull PaymentOrderRequest input) throws ApiException {
        PaymentOrderRequestLineItem requestLineItem = input.getLineItems().get(0);
        PaymentOrder paymentOrder = PaymentOrder.builder()
                .externalId(LUUID.generateUuid())
                .type(PaymentOrderType.CUSTOMER_ORDER_CHECKOUT_ONE_TIME_PAYMENT)
                .status(PaymentStatus.ACTIVE)
                .lineItems(new LinkedHashSet<>())
                .customerId(requestLineItem.getContextParam().getCustomerId())
                .storeId(requestLineItem.getContextParam().getStoreId())
                .source(input.getSource())
                .sourceService(input.getSourceService())
                .build();
        var lineItemRequests = input.getLineItems().stream().map(it -> Pair.of(paymentOrder, it))
                .collect(Collectors.toList());
        Set<PaymentOrderLineItem> lineItems = lineItemMapper.forward(lineItemRequests, new LinkedHashSet<>());
        paymentOrder.setLineItems(lineItems);
        return paymentOrder;
    }
}
