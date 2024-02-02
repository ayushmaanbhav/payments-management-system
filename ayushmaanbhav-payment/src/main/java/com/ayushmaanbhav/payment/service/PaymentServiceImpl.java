package com.ayushmaanbhav.payment.service;

import com.ayushmaanbhav.commons.contstants.*;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import com.ayushmaanbhav.commons.request.PaymentConfirmWebhookRequest;
import com.ayushmaanbhav.commons.util.LUUID;
import com.ayushmaanbhav.eventProcessor.dto.EventProcessorDtoApi;
import com.ayushmaanbhav.gatewayProvider.api.payment.GatewayProviderPaymentApi;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.CreatePaymentRequest;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.GetPaymentRequest;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.PaymentResponse;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderConfig;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderPaymentDetail;
import com.ayushmaanbhav.gatewayProvider.mapper.GatewayProviderPaymentDetailMapper;
import com.ayushmaanbhav.gatewayProvider.service.GatewayProviderConfigService;
import com.ayushmaanbhav.payment.api.payment.PaymentWebhookApi;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderRequest;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderRequestLineItem;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderResponse;
import com.ayushmaanbhav.payment.entity.PaymentOrder;
import com.ayushmaanbhav.payment.entity.PaymentOrderLineItem;
import com.ayushmaanbhav.payment.mapper.*;
import com.ayushmaanbhav.payment.util.Util;
import com.ayushmaanbhav.requestIdempotency.api.dto.SaveRequestDto;
import com.ayushmaanbhav.requestIdempotency.service.RequestIdempotencyService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PaymentServiceImpl implements PaymentServiceApi, PaymentWebhookApi {
    RequestIdempotencyService requestIdempotencyService;
    GatewayProviderConfigService gatewayConfigService;
    GatewayProviderPaymentApi gatewayPaymentApi;
    GatewayProviderPaymentDetailMapper gatewayPaymentDetailMapper;
    PaymentOrderService paymentOrderService;
    PaymentOrderLineItemService paymentOrderLineItemService;
    PaymentOrderMapper paymentOrderMapper;
    GatewayProviderCreatePaymentRequestMapper createPaymentRequestMapper;
    GatewayProviderGetPaymentRequestMapper getPaymentRequestMapper;
    PaymentOrderResponseMapper paymentOrderResponseMapper;
    PaymentExpiryServiceApi paymentExpiryService;
    PaymentStatusService paymentStatusService;
    EventProcessorDtoApi eventProcessorDtoApi;
    PaymentConfirmWebhookRequestMapper paymentConfirmWebhookRequestMapper;

    @Override
    public @NonNull PaymentOrderResponse createPayment(@NonNull PaymentOrderRequest request) throws ApiException {
        log.info("Create payment initiated");
        try {
            var requestIdempotencyDetail = requestIdempotencyService.resolveRequestMapping(request.getRequestId());
            if (RequestIdempotencyMappedIdType.ayushmaanbhav_PAYMENT_ORDER_ID.name()
                    .equals(requestIdempotencyDetail.getMappedIdType())) {
                return this.getPaymentByOrder(requestIdempotencyDetail.getMappedId());
            } else {
                log.error("Request Idempotency Detail mapped to some other existing entity {}", requestIdempotencyDetail.getMappedIdType());
                throw new ApiException("request_id mapped to some other entity", ErrorCode.BAD_REQUEST);
            }
        } catch (ApiException e) {
            if (e.getErrorCode() != ErrorCode.NOT_FOUND) {
                throw e;
            }
        }

        if (request.getType() != PaymentOrderType.CUSTOMER_ORDER_CHECKOUT_ONE_TIME_PAYMENT) {
            log.error("Only one time payments are supported");
            throw new ApiException("only one time payments are supported", ErrorCode.BAD_REQUEST);
        }
        if (request.getLineItems().size() != 1) {
            log.error("Only one item can be there for one time payment request.");
            throw new ApiException("only one item can be there for one time payment request", ErrorCode.BAD_REQUEST);
        }
        PaymentOrderRequestLineItem requestLineItem = request.getLineItems().get(0);

        Util.verifyExistence(requestLineItem.getContextParam(), "context_param is required");
        Util.verifyExistence(requestLineItem.getContextParam().getCustomerId(), "customer_id is required");
        Util.verifyExistence(requestLineItem.getContextParam().getStoreId(), "store_id is required");
        Util.verifyExistence(requestLineItem.getContextParam().getCustomerPhone(), "customer_phone is required");
        Util.verifyExistence(requestLineItem.getContextParam().getGatewayProviderConfigId(), "gateway_provider_config_id is required");
        Util.verifyExistence(requestLineItem.getContextParam().getLedgerDebitAccountId(), "gateway_provider_config_id is required");
        Util.verifyExistence(requestLineItem.getContextParam().getLedgerCreditAccountId(), "gateway_provider_config_id is required");
        Util.verifyTruth(requestLineItem.getCurrency() == Currency.INR, "only INR currency supported");
        Util.verifyTruth(requestLineItem.getNormalisedAmount() > 0, "amount should be a positive value");

        GatewayProviderConfig providerConfig = gatewayConfigService
                .get(requestLineItem.getContextParam().getGatewayProviderConfigId());
        PaymentOrder paymentOrder = paymentOrderMapper.forward(request);
        String customerPhone = requestLineItem.getContextParam().getCustomerPhone();
        PaymentOrderLineItem lineItem = paymentOrder.getLineItems().stream().findFirst().get();
        lineItem.setGatewayProviderConfig(providerConfig);
        CreatePaymentRequest gatewayRequest = createPaymentRequestMapper.forward(Pair.of(paymentOrder, customerPhone));
        PaymentResponse gatewayResponse = gatewayPaymentApi.create(gatewayRequest);
        GatewayProviderPaymentDetail gatewayPaymentDetail = gatewayPaymentDetailMapper.forward(gatewayResponse);
        lineItem.setGatewayProviderPaymentDetail(gatewayPaymentDetail);
        paymentOrder = paymentOrderService.createPaymentOrder(paymentOrder);

        requestIdempotencyService.saveRequestMapping(SaveRequestDto.builder()
                .requestId(request.getRequestId())
                .mappedId(paymentOrder.getExternalId())
                .mappedIdType(RequestIdempotencyMappedIdType.ayushmaanbhav_PAYMENT_ORDER_ID.name()).build());

        return paymentOrderResponseMapper.forward(paymentOrder);
    }

    @Override
    public @NonNull PaymentOrderResponse getPaymentByOrder(@NonNull String externalId) throws ApiException {
        log.info("Get payment initiated");
        return paymentStatusService.checkAndUpdatePaymentStatus(externalId, this::getPaymentResponse);
    }

    @Override
    public void processPaymentWebhook(@NonNull PaymentResponse paymentResponse) throws ApiException {
        log.info("Process payment webhook initiated");
        var lineItem = paymentOrderLineItemService.getByExternalId(paymentResponse.getOrderId());
        PaymentOrderResponse paymentOrderResponse = paymentStatusService
                .checkAndUpdatePaymentStatus(lineItem.getPaymentOrder().getExternalId(), paymentOrder -> paymentResponse);
        PaymentConfirmWebhookRequest webhookRequest = paymentConfirmWebhookRequestMapper.forward(paymentOrderResponse);
        eventProcessorDtoApi.createEvent(webhookRequest, EventType.PAYMENT_CONFIRM_WEBHOOK_CREATION,
                LUUID.generateUuid());
    }

    @SneakyThrows
    private @NonNull PaymentResponse getPaymentResponse(@NonNull PaymentOrder paymentOrder) {
        GetPaymentRequest gatewayRequest = getPaymentRequestMapper.forward(paymentOrder);
        return gatewayPaymentApi.get(gatewayRequest);
    }

    @Override
    public void expirePayments() throws ApiException {
        log.info("Expire Payment initiated");
        List<PaymentOrder> paymentOrderList = paymentOrderService.getByStatus(PaymentStatus.ACTIVE);
        for(PaymentOrder paymentOrder : paymentOrderList) {
            try {
                paymentExpiryService.expirePayment(paymentOrder);
            } catch (ApiException e) {
                log.error("Error occurred expiring payment {}", paymentOrder.getExternalId(), e);
            }
        }
    }
}
