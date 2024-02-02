package com.ayushmaanbhav.payment.testsetup.gatewayrequest;

import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.CreatePaymentRequest;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderConfig;
import com.ayushmaanbhav.payment.entity.PaymentOrder;
import com.ayushmaanbhav.payment.entity.PaymentOrderLineItem;
import com.ayushmaanbhav.payment.testsetup.gatewayprovider.GatewayProviderConfigDataSetup;
import com.ayushmaanbhav.payment.testsetup.paymentorder.PaymentOrderDataSetup;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;

import static com.ayushmaanbhav.payment.util.TestValues.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreatePaymentRequestDataSetup {

    public static CreatePaymentRequest getCreatePaymentRequest() {
        PaymentOrder paymentOrder = PaymentOrderDataSetup.getPaymentOrder(PaymentStatus.ACTIVE);
        GatewayProviderConfig providerConfig = GatewayProviderConfigDataSetup.getGatewayProviderConfig();
        PaymentOrderLineItem lineItem = paymentOrder.getLineItems().iterator().next();
        lineItem.setGatewayProviderConfig(providerConfig);

        return CreatePaymentRequest.builder()
                .orderId(lineItem.getExternalId())
                .gatewayProviderConfigId(providerConfig.getExternalId())
                .normalisedAmount(lineItem.getNormalisedAmount())
                .currency(lineItem.getCurrency())
                .customerPhone(PHONE)
                .params(new HashMap<>())
                .expiresInSecond(LINK_EXPIRY_TIME)
                .build();
    }
}
