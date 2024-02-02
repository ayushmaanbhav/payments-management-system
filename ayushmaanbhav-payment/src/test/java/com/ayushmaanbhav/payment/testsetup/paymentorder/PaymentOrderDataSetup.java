package com.ayushmaanbhav.payment.testsetup.paymentorder;

import com.ayushmaanbhav.commons.contstants.Currency;
import com.ayushmaanbhav.commons.contstants.PaymentOrderType;
import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.payment.entity.PaymentOrder;
import com.ayushmaanbhav.payment.entity.PaymentOrderLineItem;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.LinkedHashSet;

import static com.ayushmaanbhav.payment.util.TestValues.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentOrderDataSetup {


    public static PaymentOrder getPaymentOrder(PaymentStatus status) {
        PaymentOrder paymentOrder = PaymentOrder.builder()
                .externalId(PAYMENT_ORDER_EXTERNAL_ID)
                .type(PaymentOrderType.CUSTOMER_ORDER_CHECKOUT_ONE_TIME_PAYMENT)
                .status(status)
                .lineItems(new LinkedHashSet<>())
                .customerId(CUSTOMER_ID)
                .storeId(STORE_ID)
                .source(SOURCE)
                .sourceService(SOURCE_SERVICE)
                .build();

        PaymentOrderLineItem lineItem = PaymentOrderLineItem.builder()
                .externalId(LINE_ITEM_EXTERNAL_ID)
                .ledgerCreditAccountId(CREDIT_ACCOUNT_ID)
                .ledgerDebitAccountId(DEBIT_ACCOUNT_ID)
                .normalisedAmount(NORMALISED_AMOUNT)
                .currency(Currency.INR)
                .status(status)
                .paymentOrder(paymentOrder)
                .build();

        paymentOrder.setLineItems(Collections.singleton(lineItem));
        return paymentOrder;
    }
}
