package com.ayushmaanbhav.payment.mapper;

import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderConfig;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderPaymentDetail;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderResponse;
import com.ayushmaanbhav.payment.entity.PaymentOrder;
import com.ayushmaanbhav.payment.entity.PaymentOrderLineItem;
import com.ayushmaanbhav.payment.testsetup.gatewayprovider.GatewayProviderConfigDataSetup;
import com.ayushmaanbhav.payment.testsetup.gatewayprovider.GatewayProviderPaymentDetailDataSetup;
import com.ayushmaanbhav.payment.testsetup.paymentorder.PaymentOrderDataSetup;
import com.ayushmaanbhav.payment.testsetup.paymentorder.PaymentOrderResponseDataSetup;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentOrderResponseMapperTest extends AbstractMapperTest {

    @Autowired
    PaymentOrderResponseMapper paymentOrderResponseMapper;


    @Test
    public void testForward() throws ApiException {
        PaymentOrder paymentOrder = PaymentOrderDataSetup.getPaymentOrder(PaymentStatus.ACTIVE);
        GatewayProviderConfig providerConfig = GatewayProviderConfigDataSetup.getGatewayProviderConfig();
        PaymentOrderLineItem lineItem = paymentOrder.getLineItems().iterator().next();
        GatewayProviderPaymentDetail paymentDetail = GatewayProviderPaymentDetailDataSetup.getGatewayProviderPaymentDetail();
        lineItem.setGatewayProviderConfig(providerConfig);
        lineItem.setGatewayProviderPaymentDetail(paymentDetail);
        PaymentOrderResponse paymentOrderResponse = PaymentOrderResponseDataSetup
                .getPaymentOrderResponse(PaymentStatus.ACTIVE);
        PaymentOrderResponse mapped = paymentOrderResponseMapper.forward(paymentOrder);
        assertEquals(paymentOrderResponse.getPaymentOrderId(), mapped.getPaymentOrderId());
        assertEquals(paymentOrderResponse.getPaymentWebUrl(), mapped.getPaymentWebUrl());
        assertEquals(paymentOrderResponse.getPaymentDeepLink(), mapped.getPaymentDeepLink());
        assertEquals(paymentOrderResponse.getTransactionId(), mapped.getTransactionId());
    }
}
