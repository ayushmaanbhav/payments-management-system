package com.ayushmaanbhav.payment.testsetup.gatewayrequest;

import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.GetPaymentRequest;
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
public class GetPaymentRequestDataSetup {

    public static GetPaymentRequest getGetPaymentRequest() {
        PaymentOrder paymentOrder = PaymentOrderDataSetup.getPaymentOrder(PaymentStatus.ACTIVE);
        GatewayProviderConfig providerConfig = GatewayProviderConfigDataSetup.getGatewayProviderConfig();
        PaymentOrderLineItem lineItem = paymentOrder.getLineItems().iterator().next();
        GatewayProviderPaymentDetail paymentDetail = GatewayProviderPaymentDetailDataSetup.getGatewayProviderPaymentDetail();
        lineItem.setGatewayProviderConfig(providerConfig);
        lineItem.setGatewayProviderPaymentDetail(paymentDetail);

        return GetPaymentRequest.builder()
                .orderId(lineItem.getExternalId())
                .providerOrderId(lineItem.getGatewayProviderPaymentDetail().getProviderOrderId())
                .gatewayProviderConfigId(lineItem.getGatewayProviderConfig().getExternalId())
                .build();
    }
}
