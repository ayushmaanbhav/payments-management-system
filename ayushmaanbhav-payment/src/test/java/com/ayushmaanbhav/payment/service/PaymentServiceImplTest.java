package com.ayushmaanbhav.payment.service;

import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import com.ayushmaanbhav.commons.request.PaymentConfirmWebhookRequest;
import com.ayushmaanbhav.eventProcessor.dto.EventProcessorDtoApi;
import com.ayushmaanbhav.gatewayProvider.api.payment.GatewayProviderPaymentApi;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.CreatePaymentRequest;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.GetPaymentRequest;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.PaymentResponse;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderConfig;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderPaymentDetail;
import com.ayushmaanbhav.gatewayProvider.entity.repository.GatewayProviderPaymentDetailRepository;
import com.ayushmaanbhav.gatewayProvider.mapper.GatewayProviderPaymentDetailMapper;
import com.ayushmaanbhav.gatewayProvider.mapper.GatewayProviderPaymentDetailUpdateMapper;
import com.ayushmaanbhav.gatewayProvider.service.GatewayProviderConfigService;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderRequest;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderResponse;
import com.ayushmaanbhav.payment.entity.PaymentOrder;
import com.ayushmaanbhav.payment.entity.PaymentOrderLineItem;
import com.ayushmaanbhav.payment.mapper.GatewayProviderCreatePaymentRequestMapper;
import com.ayushmaanbhav.payment.mapper.GatewayProviderGetPaymentRequestMapper;
import com.ayushmaanbhav.payment.mapper.PaymentConfirmWebhookRequestMapper;
import com.ayushmaanbhav.payment.mapper.PaymentOrderMapper;
import com.ayushmaanbhav.payment.testsetup.dto.PaymentResponseDataSetup;
import com.ayushmaanbhav.payment.testsetup.gatewayprovider.GatewayProviderConfigSetup;
import com.ayushmaanbhav.payment.testsetup.gatewayprovider.GatewayProviderPaymentDetailDataSetup;
import com.ayushmaanbhav.payment.testsetup.gatewayrequest.CreatePaymentRequestDataSetup;
import com.ayushmaanbhav.payment.testsetup.gatewayrequest.GetPaymentRequestDataSetup;
import com.ayushmaanbhav.payment.testsetup.paymentorder.PaymentOrderDataSetup;
import com.ayushmaanbhav.payment.testsetup.paymentorder.PaymentOrderRequestDataSetup;
import com.ayushmaanbhav.payment.testsetup.paymentorder.PaymentOrderResponseDataSetup;
import com.ayushmaanbhav.payment.testsetup.paymentorder.PaymentOrderSetup;
import com.ayushmaanbhav.payment.testsetup.webhook.PaymentConfirmWebhookRequestDataSetup;
import com.ayushmaanbhav.requestIdempotency.service.RequestIdempotencyService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;


import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentServiceImplTest extends AbstractServiceTest {
    @Autowired
    GatewayProviderConfigSetup gatewayProviderConfigSetup;
    @Autowired
    PaymentOrderSetup paymentOrderSetup;
    @Mock
    PaymentOrderMapper paymentOrderMapper;
    @Mock
    GatewayProviderCreatePaymentRequestMapper createPaymentRequestMapper;
    @Mock
    GatewayProviderPaymentDetailMapper gatewayPaymentDetailMapper;
    @Mock
    PaymentOrderService paymentOrderService;
    @Mock
    RequestIdempotencyService requestIdempotencyService;
    @Mock
    GatewayProviderConfigService gatewayConfigService;
    @Mock
    GatewayProviderPaymentApi gatewayPaymentApi;
    @Mock
    GatewayProviderGetPaymentRequestMapper getPaymentRequestMapper;
    @Mock
    PaymentOrderLineItemService paymentOrderLineItemService;
    @Mock
    GatewayProviderPaymentDetailUpdateMapper gatewayPaymentDetailUpdateMapper;
    @Mock
    GatewayProviderPaymentDetailRepository gatewayPaymentDetailRepository;
    @Mock
    PaymentStatusService paymentStatusService;
    @Mock
    EventProcessorDtoApi eventProcessorDtoApi;
    @Mock
    PaymentConfirmWebhookRequestMapper paymentConfirmWebhookRequestMapper;

    @Autowired PaymentServiceImpl paymentService;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(paymentService, "requestIdempotencyService", requestIdempotencyService);
        ReflectionTestUtils.setField(paymentService, "gatewayConfigService", gatewayConfigService);
        ReflectionTestUtils.setField(paymentService, "gatewayPaymentApi", gatewayPaymentApi);
        ReflectionTestUtils.setField(paymentService, "paymentOrderMapper", paymentOrderMapper);
        ReflectionTestUtils.setField(paymentService, "createPaymentRequestMapper", createPaymentRequestMapper);
        ReflectionTestUtils.setField(paymentService, "gatewayPaymentDetailMapper", gatewayPaymentDetailMapper);
        ReflectionTestUtils.setField(paymentService, "paymentOrderService",paymentOrderService);
        ReflectionTestUtils.setField(paymentService, "getPaymentRequestMapper", getPaymentRequestMapper);
        ReflectionTestUtils.setField(paymentService, "paymentOrderLineItemService", paymentOrderLineItemService);
        ReflectionTestUtils.setField(paymentService, "paymentStatusService", paymentStatusService);
        ReflectionTestUtils.setField(paymentService, "eventProcessorDtoApi", eventProcessorDtoApi);
        ReflectionTestUtils.setField(paymentService, "paymentConfirmWebhookRequestMapper",
                paymentConfirmWebhookRequestMapper);
    }
    @Test
    public void testCreatePayment() throws ApiException {

        PaymentResponse paymentResponse = PaymentResponseDataSetup.getPaymentResponse(PaymentStatus.ACTIVE);
        GatewayProviderConfig providerConfigSetup = gatewayProviderConfigSetup.setupGatewayProviderConfig();
        PaymentOrderRequest paymentOrderRequest = PaymentOrderRequestDataSetup.getPaymentOrderRequest();
        PaymentOrder paymentOrder = PaymentOrderDataSetup.getPaymentOrder(PaymentStatus.ACTIVE);
        CreatePaymentRequest gatewayRequest = CreatePaymentRequestDataSetup.getCreatePaymentRequest();
        GatewayProviderPaymentDetail gatewayProviderPaymentDetail =
                GatewayProviderPaymentDetailDataSetup.getGatewayProviderPaymentDetail();

        PaymentOrder paymentOrderDB = paymentOrderSetup.setupPaymentOrder(PaymentStatus.ACTIVE);

        doReturn(providerConfigSetup).when(gatewayConfigService).get(any());
        doThrow(new ApiException("Request not found", ErrorCode.NOT_FOUND))
                .when(requestIdempotencyService).resolveRequestMapping(paymentOrderRequest.getRequestId());
        doReturn(paymentOrder).when(paymentOrderMapper).forward(any());
        doReturn(gatewayRequest).when(createPaymentRequestMapper).forward(any());
        doReturn(paymentResponse).when(gatewayPaymentApi).create(any());
        doReturn(gatewayProviderPaymentDetail).when(gatewayPaymentDetailMapper).forward(any());
        doReturn(paymentOrderDB).when(paymentOrderService).createPaymentOrder(any());
        doNothing().when(requestIdempotencyService).saveRequestMapping(any());

        PaymentOrderResponse paymentOrderResponse = paymentService.createPayment(paymentOrderRequest);

        assertNotNull(paymentOrderResponse);
        assertEquals(paymentResponse.getPaymentWebUrl(), paymentOrderResponse.getPaymentWebUrl());
        assertEquals(paymentResponse.getPaymentDeepLink(), paymentOrderResponse.getPaymentDeepLink());
        assertEquals(PaymentStatus.ACTIVE, paymentOrderResponse.getStatus());

        verify(requestIdempotencyService, times(1))
                .resolveRequestMapping(paymentOrderRequest.getRequestId());
        verify(gatewayConfigService, times(1)).get(any());
        verify(paymentOrderMapper, times(1)).forward(any());
        verify(createPaymentRequestMapper, times(1)).forward(any());
        verify(gatewayPaymentApi, times(1)).create(any());
        verify(gatewayPaymentDetailMapper, times(1)).forward(any());
        verify(paymentOrderService, times(1)).createPaymentOrder(any());
        verify(requestIdempotencyService, times(1)).saveRequestMapping(any());
    }

    @Test
    public void testGetPaymentResponse() throws ApiException {
        PaymentOrder paymentOrder = paymentOrderSetup.setupPaymentOrder(PaymentStatus.SUCCESS);
        PaymentOrderResponse paymentOrderResponse = PaymentOrderResponseDataSetup
                .getPaymentOrderResponse(PaymentStatus.SUCCESS);
        doReturn(paymentOrderResponse).when(paymentStatusService).checkAndUpdatePaymentStatus(any(),any());
        PaymentOrderResponse paymentOrderResponseTest = paymentService.getPaymentByOrder(paymentOrder.getExternalId());

        assertNotNull(paymentOrderResponseTest);
        assertEquals(paymentOrder.getStatus(), paymentOrderResponse.getStatus());
        verify(paymentStatusService, times(1)).checkAndUpdatePaymentStatus(any(), any());
    }

    @Test
    public void testProcessPaymentWebhook() throws ApiException {
        PaymentOrderResponse paymentOrderResponse = PaymentOrderResponseDataSetup
                .getPaymentOrderResponse(PaymentStatus.SUCCESS);
        PaymentOrder paymentOrder = paymentOrderSetup.setupPaymentOrder(PaymentStatus.SUCCESS);
        PaymentOrderLineItem lineItem = paymentOrder.getLineItems().iterator().next();
        PaymentResponse paymentResponse = PaymentResponseDataSetup.getPaymentResponse(PaymentStatus.SUCCESS);
        PaymentConfirmWebhookRequest webhookRequest = PaymentConfirmWebhookRequestDataSetup.
                getPaymentConfirmWebhookRequest(PaymentStatus.SUCCESS);

        doReturn(lineItem).when(paymentOrderLineItemService).getByExternalId(paymentResponse.getOrderId());
        doReturn(paymentOrderResponse).when(paymentStatusService).checkAndUpdatePaymentStatus(any(),any());
        doReturn(webhookRequest).when(paymentConfirmWebhookRequestMapper).forward(any());
        doNothing().when(eventProcessorDtoApi).createEvent(any(), any(), any());
        paymentService.processPaymentWebhook(paymentResponse);

        verify(paymentStatusService, times(1)).checkAndUpdatePaymentStatus(any(), any());
        verify(paymentConfirmWebhookRequestMapper, times(1)).forward(any());
        verify(eventProcessorDtoApi, times(1)).createEvent(any(), any(), any());
    }

}
