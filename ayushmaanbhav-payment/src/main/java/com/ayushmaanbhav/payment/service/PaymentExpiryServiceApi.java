package com.ayushmaanbhav.payment.service;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.payment.entity.PaymentOrder;

public interface PaymentExpiryServiceApi {

    void expirePayment(PaymentOrder paymentOrder) throws ApiException;
}
