package com.ayushmaanbhav.payment.testsetup.paymentorder;

import com.ayushmaanbhav.commons.contstants.Currency;
import com.ayushmaanbhav.commons.contstants.PaymentOrderType;
import com.ayushmaanbhav.commons.data.ContextParam;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderRequest;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderRequestLineItem;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Collections;

import static com.ayushmaanbhav.payment.util.TestValues.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentOrderRequestDataSetup {


    public static PaymentOrderRequest getPaymentOrderRequest() {
        PaymentOrderRequestLineItem lineItem = getPaymentOrderRequestLineItem();
        return PaymentOrderRequest.builder()
                .requestId(REQUEST_ID)
                .type(PaymentOrderType.CUSTOMER_ORDER_CHECKOUT_ONE_TIME_PAYMENT)
                .lineItems(Collections.singletonList(lineItem))
                .source(SOURCE)
                .sourceService(SOURCE_SERVICE)
                .build();
    }

    public static PaymentOrderRequestLineItem getPaymentOrderRequestLineItem() {
        ContextParam contextParam = ContextParam.builder()
                .gatewayProviderConfigId(PROVIDER_CONFIG_EXTERNAL_ID)
                .storeId(STORE_ID)
                .customerId(CUSTOMER_ID)
                .customerPhone(PHONE)
                .ledgerDebitAccountId(DEBIT_ACCOUNT_ID)
                .ledgerCreditAccountId(CREDIT_ACCOUNT_ID)
                .build();

        return PaymentOrderRequestLineItem.builder()
                .normalisedAmount(NORMALISED_AMOUNT)
                .currency(Currency.INR)
                .contextParam(contextParam)
                .build();
    }
}
