package com.ayushmaanbhav.payment.service;

import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.gatewayProvider.api.payment.GatewayProviderPaymentApi;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.GetPaymentRequest;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.PaymentResponse;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderResponse;
import com.ayushmaanbhav.payment.entity.PaymentOrder;
import com.ayushmaanbhav.payment.entity.PaymentOrderLineItem;
import com.ayushmaanbhav.payment.mapper.GatewayProviderGetPaymentRequestMapper;
import com.ayushmaanbhav.payment.util.Util;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;

@Log4j2
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PaymentExpiryServiceImpl implements PaymentExpiryServiceApi {
    PaymentOrderService paymentOrderService;
    PaymentOrderLineItemService paymentOrderLineItemService;
    Integer paymentValidityDurationSeconds;
    GatewayProviderGetPaymentRequestMapper getPaymentRequestMapper;
    GatewayProviderPaymentApi gatewayPaymentApi;
    PaymentStatusService paymentStatusService;


    @Autowired
    public PaymentExpiryServiceImpl(PaymentOrderService paymentOrderService,
                                    PaymentOrderLineItemService paymentOrderLineItemService,
                                    @NonNull @Value("${ayushmaanbhav.payment.order.validity.duration.seconds}")
                                    Integer paymentValidityDurationSeconds,
                                    GatewayProviderGetPaymentRequestMapper getPaymentRequestMapper,
                                    GatewayProviderPaymentApi gatewayPaymentApi,
                                    PaymentStatusService paymentStatusService) {
        this.paymentOrderService = paymentOrderService;
        this.paymentOrderLineItemService = paymentOrderLineItemService;
        this.paymentValidityDurationSeconds = paymentValidityDurationSeconds;
        this.getPaymentRequestMapper = getPaymentRequestMapper;
        this.gatewayPaymentApi = gatewayPaymentApi;
        this.paymentStatusService = paymentStatusService;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void expirePayment(PaymentOrder paymentOrder) throws ApiException {
        PaymentOrderResponse paymentOrderResponse = paymentStatusService
                .checkAndUpdatePaymentStatus(paymentOrder.getExternalId(), this::getPaymentResponse);
        if(paymentOrderResponse.getStatus().equals(PaymentStatus.SUCCESS)) {
            return;
        }
        ZonedDateTime currentDate = ZonedDateTime.now();
        if(Duration.between(paymentOrder.getCreatedOn(), currentDate).getSeconds() >= paymentValidityDurationSeconds) {
            var activeLineItems = paymentOrder.getLineItems().stream()
                    .filter(it-> it.getStatus() == PaymentStatus.ACTIVE).collect(Collectors.toList());
            Util.verifyTruth(activeLineItems.size() == 1,
                    "there can be only one active line item for a payment order");
            PaymentOrderLineItem paymentOrderLineItem = activeLineItems.get(0);
            log.info("Expiring Payment Order with external Id: " + paymentOrder.getExternalId());
            paymentOrderService.updateStatus(paymentOrder, PaymentStatus.FAIL);
            paymentOrderLineItemService.updateStatus(paymentOrderLineItem, PaymentStatus.FAIL);
        }
    }

    @SneakyThrows
    private @NonNull PaymentResponse getPaymentResponse(@NonNull PaymentOrder paymentOrder) {
        GetPaymentRequest gatewayRequest = getPaymentRequestMapper.forward(paymentOrder);
        return gatewayPaymentApi.get(gatewayRequest);
    }
}
