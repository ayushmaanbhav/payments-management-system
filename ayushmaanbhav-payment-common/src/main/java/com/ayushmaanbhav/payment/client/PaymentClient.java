package com.ayushmaanbhav.payment.client;

import com.ayushmaanbhav.client.AbstractClient;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.response.PaymentClientDataResponse;
import com.ayushmaanbhav.commons.response.PaymentClientResponse;
import com.ayushmaanbhav.payment.api.payment.dto.PaymentOrderRequest;
import lombok.NonNull;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class PaymentClient extends AbstractClient {

    public PaymentClient(String baseUrl) {
        super(baseUrl);
    }

    public @NonNull PaymentClientDataResponse createPayment(@NonNull PaymentOrderRequest request) throws ApiException {
        PaymentClientResponse response = makeRequest(HttpMethod.POST, "payment/order",
                null, request, PaymentClientResponse.class);
        return response.getData();
    }

    public @NonNull PaymentClientDataResponse getPayment(@NonNull String orderId) throws ApiException {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>() {{
            add("orderId", orderId);
        }};
        PaymentClientResponse response = makeRequest(HttpMethod.GET, "payment/order",
                null, params, null, PaymentClientResponse.class);
        return response.getData();
    }


}
