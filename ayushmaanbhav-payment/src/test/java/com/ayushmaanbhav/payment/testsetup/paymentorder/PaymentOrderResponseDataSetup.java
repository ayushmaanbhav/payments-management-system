package com.ayushmaanbhav.payment.testsetup.paymentorder;

import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderConfig;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderPaymentDetail;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderResponse;
import com.ayushmaanbhav.payment.entity.PaymentOrder;
import com.ayushmaanbhav.payment.entity.PaymentOrderLineItem;
import com.ayushmaanbhav.payment.testsetup.gatewayprovider.GatewayProviderConfigDataSetup;
import com.ayushmaanbhav.payment.testsetup.gatewayprovider.GatewayProviderPaymentDetailDataSetup;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentOrderResponseDataSetup {

    public static PaymentOrderResponse getPaymentOrderResponse(PaymentStatus status) {
        PaymentOrder paymentOrder = PaymentOrderDataSetup.getPaymentOrder(status);
        GatewayProviderConfig providerConfig = GatewayProviderConfigDataSetup.getGatewayProviderConfig();
        PaymentOrderLineItem lineItem = paymentOrder.getLineItems().iterator().next();
        GatewayProviderPaymentDetail paymentDetail = GatewayProviderPaymentDetailDataSetup.getGatewayProviderPaymentDetail();
        lineItem.setGatewayProviderConfig(providerConfig);
        lineItem.setGatewayProviderPaymentDetail(paymentDetail);

        return PaymentOrderResponse.builder()
                .paymentOrderId(paymentOrder.getExternalId())
                .status(paymentOrder.getStatus())
                .paymentWebUrl(paymentDetail.getPaymentWebUrl())
                .paymentDeepLink(paymentDetail.getPaymentDeepLink())
                .transactionId(lineItem.getExternalId())
                .build();
    }
}
