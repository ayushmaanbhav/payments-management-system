package com.ayushmaanbhav.payment.api.payment;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderRequest;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderResponse;
import com.ayushmaanbhav.service.commons.model.response.GenericResponse;
import lombok.NonNull;
import org.springframework.web.bind.annotation.*;

@RequestMapping("payment")
public interface PaymentApi {

    @PostMapping("order")
    @NonNull GenericResponse<PaymentOrderResponse> createPayment(@NonNull @RequestBody PaymentOrderRequest request)
            throws ApiException;

    @GetMapping("order")
    @NonNull GenericResponse<PaymentOrderResponse> getPaymentOrder(@NonNull @RequestParam("orderId") String orderId)
            throws ApiException;

    @PostMapping("expire/cron")
    void expirePayment() throws ApiException;
}
