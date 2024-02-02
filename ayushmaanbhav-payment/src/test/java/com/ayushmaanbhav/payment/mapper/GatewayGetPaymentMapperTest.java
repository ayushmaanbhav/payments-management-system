package com.ayushmaanbhav.payment.mapper;

import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.GetPaymentRequest;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderConfig;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderPaymentDetail;
import com.ayushmaanbhav.payment.entity.PaymentOrder;
import com.ayushmaanbhav.payment.entity.PaymentOrderLineItem;
import com.ayushmaanbhav.payment.testsetup.gatewayprovider.GatewayProviderConfigDataSetup;
import com.ayushmaanbhav.payment.testsetup.gatewayprovider.GatewayProviderPaymentDetailDataSetup;
import com.ayushmaanbhav.payment.testsetup.gatewayrequest.GetPaymentRequestDataSetup;
import com.ayushmaanbhav.payment.testsetup.paymentorder.PaymentOrderDataSetup;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;


@FieldDefaults(level = AccessLevel.PRIVATE)
public class GatewayGetPaymentMapperTest extends AbstractMapperTest {

    @Autowired
    GatewayProviderGetPaymentRequestMapper getPaymentRequestMapper;

    @Test
    public void testForward() throws ApiException {
        PaymentOrder paymentOrder = PaymentOrderDataSetup.getPaymentOrder(PaymentStatus.ACTIVE);
        GatewayProviderConfig providerConfig = GatewayProviderConfigDataSetup.getGatewayProviderConfig();
        PaymentOrderLineItem lineItem = paymentOrder.getLineItems().iterator().next();
        GatewayProviderPaymentDetail paymentDetail = GatewayProviderPaymentDetailDataSetup.getGatewayProviderPaymentDetail();
        lineItem.setGatewayProviderConfig(providerConfig);
        lineItem.setGatewayProviderPaymentDetail(paymentDetail);
        GetPaymentRequest getPaymentRequest = GetPaymentRequestDataSetup.getGetPaymentRequest();
        GetPaymentRequest mapped = getPaymentRequestMapper.forward(paymentOrder);
        assertEquals(getPaymentRequest.getOrderId(), mapped.getOrderId());
        assertEquals(getPaymentRequest.getProviderOrderId(), mapped.getProviderOrderId());
        assertEquals(getPaymentRequest.getProviderOrderId(), mapped.getProviderOrderId());
    }
}
