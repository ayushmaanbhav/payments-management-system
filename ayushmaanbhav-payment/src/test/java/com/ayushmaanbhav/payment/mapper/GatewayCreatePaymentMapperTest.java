package com.ayushmaanbhav.payment.mapper;

import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.CreatePaymentRequest;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderConfig;
import com.ayushmaanbhav.payment.entity.PaymentOrder;
import com.ayushmaanbhav.payment.entity.PaymentOrderLineItem;
import com.ayushmaanbhav.payment.testsetup.gatewayprovider.GatewayProviderConfigDataSetup;
import com.ayushmaanbhav.payment.testsetup.gatewayrequest.CreatePaymentRequestDataSetup;
import com.ayushmaanbhav.payment.testsetup.paymentorder.PaymentOrderDataSetup;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;

import static com.ayushmaanbhav.payment.util.TestValues.PHONE;
import static org.junit.Assert.assertEquals;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class GatewayCreatePaymentMapperTest extends AbstractMapperTest {
    @Autowired
    GatewayProviderCreatePaymentRequestMapper gatewayProviderCreatePaymentRequestMapper;
    @Test
    public void testForward() throws ApiException {
        PaymentOrder paymentOrder = PaymentOrderDataSetup.getPaymentOrder(PaymentStatus.ACTIVE);
        GatewayProviderConfig providerConfig = GatewayProviderConfigDataSetup.getGatewayProviderConfig();
        PaymentOrderLineItem lineItem = paymentOrder.getLineItems().iterator().next();
        lineItem.setGatewayProviderConfig(providerConfig);
        CreatePaymentRequest createPaymentRequest = CreatePaymentRequestDataSetup.getCreatePaymentRequest();
        CreatePaymentRequest mapped = gatewayProviderCreatePaymentRequestMapper.forward(Pair.of(paymentOrder, PHONE));
        assertEquals(createPaymentRequest.getOrderId(), mapped.getOrderId());
        assertEquals(createPaymentRequest.getGatewayProviderConfigId(), mapped.getGatewayProviderConfigId());
        assertEquals(createPaymentRequest.getNormalisedAmount(), mapped.getNormalisedAmount());
        assertEquals(createPaymentRequest.getCurrency(), mapped.getCurrency());
        assertEquals(createPaymentRequest.getExpiresInSecond(), mapped.getExpiresInSecond());
        assertEquals(createPaymentRequest.getCustomerPhone(), mapped.getCustomerPhone());
    }
}
