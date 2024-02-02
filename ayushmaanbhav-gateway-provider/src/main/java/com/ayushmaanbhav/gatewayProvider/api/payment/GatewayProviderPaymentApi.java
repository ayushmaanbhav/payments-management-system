package com.ayushmaanbhav.gatewayProvider.api.payment;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.CreatePaymentRequest;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.GetPaymentRequest;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.PaymentResponse;
import lombok.NonNull;


public interface GatewayProviderPaymentApi {

    @NonNull PaymentResponse create(@NonNull CreatePaymentRequest request) throws ApiException;

    @NonNull PaymentResponse get(@NonNull GetPaymentRequest request) throws ApiException;
}
