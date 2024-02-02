package com.ayushmaanbhav.payment.service;

import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderConfig;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderPaymentDetail;
import com.ayushmaanbhav.payment.entity.PaymentOrder;
import com.ayushmaanbhav.payment.entity.PaymentOrderLineItem;
import com.ayushmaanbhav.payment.testsetup.gatewayprovider.GatewayProviderConfigSetup;
import com.ayushmaanbhav.payment.testsetup.gatewayprovider.GatewayProviderPaymentDetailDataSetup;
import com.ayushmaanbhav.payment.testsetup.paymentorder.PaymentOrderDataSetup;
import com.ayushmaanbhav.payment.testsetup.paymentorder.PaymentOrderSetup;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentOrderServiceTest extends AbstractServiceTest {
    private static final String NON_EXISTENT_EXTERNAL_ID = "eg8a0289a62146ed9a1ab47660e2c641";
    @Autowired
    PaymentOrderSetup paymentOrderSetup;
    @Autowired
    PaymentOrderService paymentOrderService;
    @Autowired
    GatewayProviderConfigSetup configSetup;

    @Test
    public void testGetByExternalId() throws ApiException {
        PaymentOrder paymentOrder = paymentOrderSetup.setupPaymentOrder(PaymentStatus.ACTIVE);
        PaymentOrder inDB = paymentOrderService.getByExternalId(paymentOrder.getExternalId());
        assertNotNull(inDB);
        assertEquals(paymentOrder.getId(), inDB.getId());
        try {
            paymentOrderService.getByExternalId(NON_EXISTENT_EXTERNAL_ID);
            fail("Should have thrown exception");
        } catch (ApiException e) {
            assertEquals(ErrorCode.NOT_FOUND, e.getErrorCode());
        }
    }

    @Test
    public void testCreate() throws ApiException {
        PaymentOrder paymentOrder = PaymentOrderDataSetup.getPaymentOrder(PaymentStatus.ACTIVE);
        GatewayProviderConfig providerConfig = configSetup.setupGatewayProviderConfig();
        GatewayProviderPaymentDetail paymentDetail = GatewayProviderPaymentDetailDataSetup
                .getGatewayProviderPaymentDetail();
        PaymentOrderLineItem lineItem = paymentOrder.getLineItems().iterator().next();
        lineItem.setGatewayProviderConfig(providerConfig);
        lineItem.setGatewayProviderPaymentDetail(paymentDetail);
        paymentOrder = paymentOrderService.createPaymentOrder(paymentOrder);
        PaymentOrder inDB = paymentOrderService.getByExternalId(paymentOrder.getExternalId());
        assertNotNull(inDB);
    }

    @Test
    public void testUpdateStatus() throws ApiException {
        PaymentOrder paymentOrderActive = paymentOrderSetup.setupPaymentOrder(PaymentStatus.ACTIVE);
        PaymentOrder paymentOrderUpdated = paymentOrderService.updateStatus(paymentOrderActive, PaymentStatus.SUCCESS);
        assertEquals(PaymentStatus.SUCCESS, paymentOrderUpdated.getStatus());
        paymentOrderActive = paymentOrderSetup.setupPaymentOrder(PaymentStatus.ACTIVE);
        paymentOrderUpdated = paymentOrderService.updateStatus(paymentOrderActive, PaymentStatus.FAIL);
        assertEquals(PaymentStatus.FAIL, paymentOrderUpdated.getStatus());
        paymentOrderActive = paymentOrderSetup.setupPaymentOrder(PaymentStatus.ACTIVE);
        try {
            paymentOrderService.updateStatus(paymentOrderActive, PaymentStatus.ACTIVE);
            fail("Should throw exception");
        } catch (ApiException e) {
            assertEquals(ErrorCode.BAD_REQUEST, e.getErrorCode());
        }
        PaymentOrder paymentOrderSuccess = paymentOrderSetup.setupPaymentOrder(PaymentStatus.SUCCESS);
        try {
            paymentOrderService.updateStatus(paymentOrderSuccess, PaymentStatus.ACTIVE);
            fail("Should throw exception");
        } catch (ApiException e) {
            assertEquals(ErrorCode.BAD_REQUEST, e.getErrorCode());
        }
        try {
            paymentOrderService.updateStatus(paymentOrderSuccess, PaymentStatus.FAIL);
            fail("Should throw exception");
        } catch (ApiException e) {
            assertEquals(ErrorCode.BAD_REQUEST, e.getErrorCode());
        }
        try {
            paymentOrderService.updateStatus(paymentOrderSuccess, PaymentStatus.SUCCESS);
            fail("Should throw exception");
        } catch (ApiException e) {
            assertEquals(ErrorCode.BAD_REQUEST, e.getErrorCode());
        }
        PaymentOrder paymentOrderFail = paymentOrderSetup.setupPaymentOrder(PaymentStatus.FAIL);
        try {
            paymentOrderService.updateStatus(paymentOrderFail, PaymentStatus.ACTIVE);
            fail("Should throw exception");
        } catch (ApiException e) {
            assertEquals(ErrorCode.BAD_REQUEST, e.getErrorCode());
        }
        try {
            paymentOrderService.updateStatus(paymentOrderFail, PaymentStatus.FAIL);
            fail("Should throw exception");
        } catch (ApiException e) {
            assertEquals(ErrorCode.BAD_REQUEST, e.getErrorCode());
        }
        try {
            paymentOrderService.updateStatus(paymentOrderFail, PaymentStatus.SUCCESS);
            fail("Should throw exception");
        } catch (ApiException e) {
            assertEquals(ErrorCode.BAD_REQUEST, e.getErrorCode());
        }
    }

}
