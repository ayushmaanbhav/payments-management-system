package com.ayushmaanbhav.payment.mapper;

import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.mapper.OneWayMapper;
import com.ayushmaanbhav.commons.util.LUUID;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderRequestLineItem;
import com.ayushmaanbhav.payment.entity.PaymentOrder;
import com.ayushmaanbhav.payment.entity.PaymentOrderLineItem;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentOrderLineItemMapper
        implements OneWayMapper<Pair<PaymentOrder, PaymentOrderRequestLineItem>, PaymentOrderLineItem> {

    @Override
    public @NonNull PaymentOrderLineItem forward(@NonNull Pair<PaymentOrder, PaymentOrderRequestLineItem> input)
            throws ApiException {
        PaymentOrder paymentOrder = input.getFirst();
        PaymentOrderRequestLineItem lineItemRequest = input.getSecond();
        return PaymentOrderLineItem.builder()
                .externalId(LUUID.generateUuid())
                .ledgerCreditAccountId(lineItemRequest.getContextParam().getLedgerCreditAccountId())
                .ledgerDebitAccountId(lineItemRequest.getContextParam().getLedgerDebitAccountId())
                .normalisedAmount(lineItemRequest.getNormalisedAmount())
                .currency(lineItemRequest.getCurrency())
                .status(PaymentStatus.ACTIVE)
                .paymentOrder(paymentOrder)
                .build();
    }
}
