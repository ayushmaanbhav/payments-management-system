package com.ayushmaanbhav.payment.service;

import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.payment.entity.PaymentOrderLineItem;
import com.ayushmaanbhav.payment.entity.repository.PaymentOrderLineItemRepository;
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
public class PaymentOrderLineItemService {
    static final Map<PaymentStatus, List<PaymentStatus>> paymentOrderLineItemStatusMap = new HashMap<>() {{
        put(ACTIVE, asList(SUCCESS, FAIL));
        put(SUCCESS, List.of());
        put(FAIL, List.of());
    }};

    PaymentOrderLineItemRepository dao;

    public PaymentOrderLineItem getByExternalId(String externalId) throws ApiException {
        PaymentOrderLineItem paymentOrderLineItem = dao.selectByExternalId(externalId);
        Util.verifyExistence(paymentOrderLineItem, "Payment order line item not found");
        return paymentOrderLineItem;
    }

    public PaymentOrderLineItem createPaymentOrderLineItem(
            PaymentOrderLineItem lineItem
    ) throws ApiException {
        lineItem.setId(null);
        lineItem.setPaidDate(null);
        lineItem.setExpiredDate(null);
        lineItem.setStatus(ACTIVE);
        Util.verifyExistence(lineItem.getExternalId(), "missing external_id in line_item");
        dao.save(lineItem);
        log.info("Creating Payment Order with external Id = " + lineItem.getExternalId() +", order status set to " +
                ACTIVE.name());
        return lineItem;
    }

    public PaymentOrderLineItem updateStatus(
            PaymentOrderLineItem paymentOrderLineItem, PaymentStatus newStatus
    ) throws ApiException {
        validateUpdate(paymentOrderLineItem.getStatus(), newStatus);
        paymentOrderLineItem.setStatus(newStatus);
        dao.update(paymentOrderLineItem);
        log.info("Setting status of Payment Order Line Item with external Id = " + paymentOrderLineItem.getExternalId()
                + " to " + newStatus.name());
        return paymentOrderLineItem;
    }

    //validate payment order status update
    public void validateUpdate(PaymentStatus currentState, PaymentStatus newState) throws ApiException {
        if (!paymentOrderLineItemStatusMap.get(currentState).contains(newState)) {
            log.error("Current status of Payment Order Line Item is " + currentState.name() + ", and new status is " +
                    newState.name());
            throw new ApiException("Payment Order Line Item status is " + currentState, BAD_REQUEST);
        }
    }

}
