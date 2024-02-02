package com.ayushmaanbhav.payment.service;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderRequest;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderResponse;

public interface PaymentServiceApi {

    PaymentOrderResponse createPayment(PaymentOrderRequest request) throws ApiException;

    PaymentOrderResponse getPaymentByOrder(String orderId) throws ApiException;

    void expirePayments() throws ApiException;

}
