package com.ayushmaanbhav.payment.service;

import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.GetPaymentRequest;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.PaymentResponse;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderPaymentDetail;
import com.ayushmaanbhav.gatewayProvider.entity.repository.GatewayProviderPaymentDetailRepository;
import com.ayushmaanbhav.gatewayProvider.mapper.GatewayProviderPaymentDetailUpdateMapper;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderResponse;
import com.ayushmaanbhav.payment.entity.PaymentOrder;
import com.ayushmaanbhav.payment.entity.PaymentOrderLineItem;
import com.ayushmaanbhav.payment.testsetup.dto.PaymentResponseDataSetup;
import com.ayushmaanbhav.payment.testsetup.gatewayprovider.GatewayProviderPaymentDetailDataSetup;
import com.ayushmaanbhav.payment.testsetup.paymentorder.PaymentOrderSetup;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentStatusServiceTest extends AbstractServiceTest {

    @Mock
    GatewayProviderPaymentDetailUpdateMapper gatewayPaymentDetailUpdateMapper;
    @Mock
    PaymentOrderService paymentOrderService;
    @Mock
    PaymentOrderLineItemService paymentOrderLineItemService;
    @Mock
    GatewayProviderPaymentDetailRepository gatewayPaymentDetailRepository;
    @Autowired
    PaymentStatusService paymentStatusService;
    @Autowired
    PaymentOrderSetup paymentOrderSetup;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(paymentStatusService, "paymentOrderService",paymentOrderService);
        ReflectionTestUtils.setField(paymentStatusService, "paymentOrderLineItemService", paymentOrderLineItemService);
        ReflectionTestUtils.setField(paymentStatusService, "gatewayPaymentDetailUpdateMapper", gatewayPaymentDetailUpdateMapper);
        ReflectionTestUtils.setField(paymentStatusService, "gatewayPaymentDetailRepository", gatewayPaymentDetailRepository);
    }

    @Test
    public void testCheckAndUpdatePaymentStatusSuccess() throws ApiException {
        PaymentOrder paymentOrderActive = paymentOrderSetup.setupPaymentOrder(PaymentStatus.ACTIVE);
        PaymentResponse paymentResponse = PaymentResponseDataSetup.getPaymentResponse(PaymentStatus.SUCCESS);
        GatewayProviderPaymentDetail paymentDetail = GatewayProviderPaymentDetailDataSetup
                .getGatewayProviderPaymentDetail();
        PaymentOrder paymentOrderSuccess = paymentOrderSetup.setupPaymentOrder(PaymentStatus.SUCCESS);
        PaymentOrderLineItem successLineItem = paymentOrderSuccess.getLineItems().iterator().next();

        Function<PaymentOrder, PaymentResponse> mockPaymentResponseSupplier = mock(Function.class);

        doReturn(paymentOrderActive).when(paymentOrderService).getByExternalId(paymentOrderActive.getExternalId());
        doReturn(paymentResponse).when(mockPaymentResponseSupplier).apply(any());
        doReturn(paymentDetail).when(gatewayPaymentDetailUpdateMapper).forward(any());
        doReturn(paymentOrderSuccess).when(paymentOrderService).updateStatus(any(), any());
        doReturn(successLineItem).when(paymentOrderLineItemService).updateStatus(any(), any());

        PaymentOrderResponse paymentOrderResponse = paymentStatusService.checkAndUpdatePaymentStatus(paymentOrderActive.getExternalId(), mockPaymentResponseSupplier);

        assertNotNull(paymentOrderResponse);
        assertEquals(paymentResponse.getPaymentWebUrl(), paymentOrderResponse.getPaymentWebUrl());
        assertEquals(paymentResponse.getPaymentDeepLink(), paymentOrderResponse.getPaymentDeepLink());
        assertEquals(PaymentStatus.SUCCESS, paymentOrderResponse.getStatus());

        verify(paymentOrderService, times(1))
                .getByExternalId(paymentOrderActive.getExternalId());
        verify(mockPaymentResponseSupplier, times(1)).apply(any());
        verify(gatewayPaymentDetailUpdateMapper, times(1)).forward(any());
        verify(paymentOrderService, times(1)).updateStatus(any(), any());
        verify(paymentOrderLineItemService, times(1)).updateStatus(any(), any());
    }

    @Test
    public void testCheckAndUpdatePaymentStatusNotSuccess() throws ApiException {
        PaymentOrder paymentOrderActive = paymentOrderSetup.setupPaymentOrder(PaymentStatus.ACTIVE);
        PaymentResponse paymentResponse = PaymentResponseDataSetup.getPaymentResponse(PaymentStatus.ACTIVE);
        Function<PaymentOrder, PaymentResponse> mockPaymentResponseSupplier = mock(Function.class);

        doReturn(paymentOrderActive).when(paymentOrderService).getByExternalId(paymentOrderActive.getExternalId());
        doReturn(paymentResponse).when(mockPaymentResponseSupplier).apply(any());
        doNothing().when(gatewayPaymentDetailRepository).save(any());

        PaymentOrderResponse paymentOrderResponse = paymentStatusService
                .checkAndUpdatePaymentStatus(paymentOrderActive.getExternalId(), mockPaymentResponseSupplier);

        assertNotNull(paymentOrderResponse);
        assertEquals(paymentResponse.getPaymentWebUrl(), paymentOrderResponse.getPaymentWebUrl());
        assertEquals(paymentResponse.getPaymentDeepLink(), paymentOrderResponse.getPaymentDeepLink());
        assertEquals(PaymentStatus.ACTIVE, paymentOrderResponse.getStatus());

        verify(paymentOrderService, times(1))
                .getByExternalId(paymentOrderActive.getExternalId());
        verify(mockPaymentResponseSupplier, times(1)).apply(any());
        verify(gatewayPaymentDetailRepository, times(1)).save(any());
    }


}
