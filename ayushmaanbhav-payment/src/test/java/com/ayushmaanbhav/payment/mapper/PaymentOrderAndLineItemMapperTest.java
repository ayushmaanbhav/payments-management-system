package com.ayushmaanbhav.payment.mapper;

import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderRequest;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderRequestLineItem;
import com.ayushmaanbhav.payment.entity.PaymentOrder;
import com.ayushmaanbhav.payment.entity.PaymentOrderLineItem;
import com.ayushmaanbhav.payment.testsetup.paymentorder.PaymentOrderDataSetup;
import com.ayushmaanbhav.payment.testsetup.paymentorder.PaymentOrderRequestDataSetup;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;

import static org.junit.Assert.assertEquals;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentOrderAndLineItemMapperTest extends AbstractMapperTest {
    @Autowired
    PaymentOrderMapper paymentOrderMapper;
    @Autowired
    PaymentOrderLineItemMapper paymentOrderLineItemMapper;

    @Test
    public void testForwardOfPaymentOrder() throws ApiException {
        PaymentOrderRequest paymentOrderRequest = PaymentOrderRequestDataSetup.getPaymentOrderRequest();
        PaymentOrder paymentOrder = PaymentOrderDataSetup.getPaymentOrder(PaymentStatus.ACTIVE);
        PaymentOrder mapped = paymentOrderMapper.forward(paymentOrderRequest);
        assertEquals(paymentOrder.getCustomerId(), mapped.getCustomerId());
        assertEquals(paymentOrder.getStoreId(), mapped.getStoreId());
        assertEquals(paymentOrder.getSource(), mapped.getSource());
        assertEquals(paymentOrder.getSourceService(), mapped.getSourceService());
        assertEquals(paymentOrder.getStatus(), mapped.getStatus());
    }

    @Test
    public void testForwardOfPaymentOrderLineItem() throws ApiException {
        PaymentOrderRequest paymentOrderRequest = PaymentOrderRequestDataSetup.getPaymentOrderRequest();
        PaymentOrderRequestLineItem requestLineItem = paymentOrderRequest.getLineItems().get(0);
        PaymentOrder paymentOrder = PaymentOrderDataSetup.getPaymentOrder(PaymentStatus.ACTIVE);
        PaymentOrderLineItem lineItem = paymentOrder.getLineItems().iterator().next();
        PaymentOrderLineItem mapped = paymentOrderLineItemMapper.forward(Pair.of(paymentOrder, requestLineItem));
        assertEquals(lineItem.getCurrency(), mapped.getCurrency());
        assertEquals(lineItem.getNormalisedAmount(), mapped.getNormalisedAmount());
        assertEquals(lineItem.getLedgerCreditAccountId(), mapped.getLedgerCreditAccountId());
        assertEquals(lineItem.getLedgerDebitAccountId(), mapped.getLedgerDebitAccountId());
        assertEquals(lineItem.getStatus(), mapped.getStatus());
    }

}
