package com.ayushmaanbhav.payment.controller;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.payment.api.payment.PaymentApi;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderRequest;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderResponse;
import com.ayushmaanbhav.payment.service.PaymentExpiryServiceApi;
import com.ayushmaanbhav.payment.service.PaymentServiceApi;
import com.ayushmaanbhav.service.commons.model.response.GenericResponse;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController implements PaymentApi {
    PaymentServiceApi paymentService;

    @Override
    public @NonNull GenericResponse<PaymentOrderResponse> createPayment(@NonNull PaymentOrderRequest request)
            throws ApiException {
        return GenericResponse.fromData(paymentService.createPayment(request));
    }

    @Override
    public @NonNull GenericResponse<PaymentOrderResponse> getPaymentOrder(@NonNull String orderId)
            throws ApiException {
        return GenericResponse.fromData(paymentService.getPaymentByOrder(orderId));
    }

    @Override
    public void expirePayment() throws ApiException {
        paymentService.expirePayments();
    }
}
