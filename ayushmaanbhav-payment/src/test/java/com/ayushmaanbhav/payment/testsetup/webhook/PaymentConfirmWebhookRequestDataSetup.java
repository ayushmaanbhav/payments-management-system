package com.ayushmaanbhav.payment.testsetup.webhook;

import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.commons.request.PaymentConfirmWebhookRequest;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderConfig;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderPaymentDetail;
import com.ayushmaanbhav.payment.entity.PaymentOrder;
import com.ayushmaanbhav.payment.entity.PaymentOrderLineItem;
import com.ayushmaanbhav.payment.testsetup.gatewayprovider.GatewayProviderConfigDataSetup;
import com.ayushmaanbhav.payment.testsetup.gatewayprovider.GatewayProviderPaymentDetailDataSetup;
import com.ayushmaanbhav.payment.testsetup.paymentorder.PaymentOrderDataSetup;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentConfirmWebhookRequestDataSetup {

    public static PaymentConfirmWebhookRequest getPaymentConfirmWebhookRequest(PaymentStatus status) {
        PaymentOrder paymentOrder = PaymentOrderDataSetup.getPaymentOrder(status);
        GatewayProviderConfig providerConfig = GatewayProviderConfigDataSetup.getGatewayProviderConfig();
        PaymentOrderLineItem lineItem = paymentOrder.getLineItems().iterator().next();
        GatewayProviderPaymentDetail paymentDetail = GatewayProviderPaymentDetailDataSetup.getGatewayProviderPaymentDetail();
        lineItem.setGatewayProviderConfig(providerConfig);
        lineItem.setGatewayProviderPaymentDetail(paymentDetail);

        return PaymentConfirmWebhookRequest.builder()
                .paymentOrderId(paymentOrder.getExternalId())
                .status(status)
                .transactionId(lineItem.getExternalId())
                .build();

    }
}
