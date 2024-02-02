package com.ayushmaanbhav.payment.service;

import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.payment.entity.PaymentOrder;
import com.ayushmaanbhav.payment.entity.repository.PaymentOrderRepository;
import com.ayushmaanbhav.payment.util.Util;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ayushmaanbhav.commons.contstants.PaymentStatus.*;
import static com.ayushmaanbhav.commons.exception.ErrorCode.BAD_REQUEST;
import static java.util.Arrays.asList;

@Log4j2
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PaymentOrderService {
    PaymentOrderLineItemService lineItemService;

    static final Map<PaymentStatus, List<PaymentStatus>> paymentOrderStatusMap = new HashMap<>() {{
        put(ACTIVE, asList(SUCCESS, FAIL));
        put(SUCCESS, List.of());
        put(FAIL, List.of());
    }};

    PaymentOrderRepository dao;

    public PaymentOrder getByExternalId(String externalId) throws ApiException {
        PaymentOrder paymentOrder = dao.selectByExternalId(externalId);
        Util.verifyExistence(paymentOrder, "Payment order not found");
        return paymentOrder;
    }

    public PaymentOrder createPaymentOrder(PaymentOrder paymentOrder) throws ApiException {
        paymentOrder.setId(null);
        paymentOrder.setStatus(ACTIVE);
        Util.verifyExistence(paymentOrder.getExternalId(), "missing external_id in payment_order");
        dao.save(paymentOrder);
        log.info("Creating Payment Order with external Id = " + paymentOrder.getExternalId() +", order status set to " +
                ACTIVE.name());
        return paymentOrder;
    }

    public PaymentOrder updateStatus(PaymentOrder paymentOrder, PaymentStatus newStatus) throws ApiException {
        validateUpdate(paymentOrder.getStatus(), newStatus);
        paymentOrder.setStatus(newStatus);
        dao.update(paymentOrder);
        log.info("Setting status of Payment Order with external Id = " + paymentOrder.getExternalId()
                + " to " + newStatus.name());
        return paymentOrder;
    }

    //validate payment order status update
    public void validateUpdate(PaymentStatus currentState, PaymentStatus newState) throws ApiException {
        if (!paymentOrderStatusMap.get(currentState).contains(newState)) {
            log.error("Current status of Payment Order is " + currentState.name() + ", and new status is " +
                    newState.name());
            throw new ApiException("Payment order status is " + currentState, BAD_REQUEST);
        }
    }

    public List<PaymentOrder> getByStatus(PaymentStatus status) {
        return dao.selectByStatus(status);
    }

}
