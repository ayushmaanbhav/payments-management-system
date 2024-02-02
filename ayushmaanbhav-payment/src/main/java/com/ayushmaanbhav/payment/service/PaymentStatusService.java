package com.ayushmaanbhav.payment.service;

import com.ayushmaanbhav.commons.contstants.PaymentOrderType;
import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.PaymentResponse;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderPaymentDetail;
import com.ayushmaanbhav.gatewayProvider.entity.repository.GatewayProviderPaymentDetailRepository;
import com.ayushmaanbhav.gatewayProvider.mapper.GatewayProviderPaymentDetailUpdateMapper;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderResponse;
import com.ayushmaanbhav.payment.entity.PaymentOrder;
import com.ayushmaanbhav.payment.entity.PaymentOrderLineItem;
import com.ayushmaanbhav.payment.mapper.PaymentOrderResponseMapper;
import com.ayushmaanbhav.payment.util.Util;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PaymentStatusService {
    GatewayProviderPaymentDetailUpdateMapper gatewayPaymentDetailUpdateMapper;
    PaymentOrderService paymentOrderService;
    PaymentOrderLineItemService paymentOrderLineItemService;
    PaymentOrderResponseMapper paymentOrderResponseMapper;
    GatewayProviderPaymentDetailRepository gatewayPaymentDetailRepository;

    public @NonNull PaymentOrderResponse checkAndUpdatePaymentStatus(
            @NonNull String externalId, @NonNull Function<PaymentOrder, PaymentResponse> paymentResponseSupplier
    ) throws ApiException {
        PaymentOrder paymentOrder = paymentOrderService.getByExternalId(externalId);
        Util.verifyTruth(paymentOrder.getType() == PaymentOrderType.CUSTOMER_ORDER_CHECKOUT_ONE_TIME_PAYMENT,
                "invalid payment order type");

        if (paymentOrder.getStatus().equals(PaymentStatus.ACTIVE)) {
            var activeLineItems = paymentOrder.getLineItems().stream()
                    .filter(it-> it.getStatus() == PaymentStatus.ACTIVE).collect(Collectors.toList());
            Util.verifyTruth(activeLineItems.size() == 1,
                    "there can be only one active line item for a payment order");

            PaymentOrderLineItem paymentOrderLineItem = activeLineItems.get(0);
            Util.verifyExistence(paymentOrderLineItem.getGatewayProviderPaymentDetail(),
                    "provider payment detail is required");
            Util.verifyExistence(paymentOrderLineItem.getGatewayProviderPaymentDetail().getProviderOrderId(),
                    "provider order Id is required");

            PaymentResponse gatewayResponse = paymentResponseSupplier.apply(paymentOrder);
            if (gatewayResponse.getStatus() != PaymentStatus.ACTIVE) {
                GatewayProviderPaymentDetail gatewayPaymentDetail = gatewayPaymentDetailUpdateMapper
                        .forward(Pair.of(paymentOrderLineItem.getGatewayProviderPaymentDetail(), gatewayResponse));
                paymentOrderLineItem.setGatewayProviderPaymentDetail(gatewayPaymentDetail);
                paymentOrder = paymentOrderService.updateStatus(paymentOrder, gatewayResponse.getStatus());
                paymentOrderLineItem = paymentOrderLineItemService
                        .updateStatus(paymentOrderLineItem, gatewayResponse.getStatus());
            } else {
                GatewayProviderPaymentDetail gatewayPaymentDetail = paymentOrderLineItem
                        .getGatewayProviderPaymentDetail();
                gatewayPaymentDetail.setProviderStatus(gatewayResponse.getProviderStatus());
                gatewayPaymentDetailRepository.save(gatewayPaymentDetail);
            }
        }
        return paymentOrderResponseMapper.forward(paymentOrder);
    }
}
