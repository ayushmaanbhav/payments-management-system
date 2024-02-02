package com.ayushmaanbhav.client.communication;

import com.ayushmaanbhav.client.AbstractClient;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.request.SmsRequest;
import com.ayushmaanbhav.commons.response.OrderResponse;
import org.springframework.http.HttpMethod;

public class CommunicationClient extends AbstractClient {
    public CommunicationClient(String baseUrl, String apiKey) {
        super(baseUrl, apiKey);
    }

    public void sendOtp(String mobile, String otp) throws ApiException {
        SmsRequest request = new SmsRequest();
        request.setMobile(mobile);
        request.setOtp(otp);
        makeRequest(HttpMethod.POST, "sms/otp", null, request, Void.class);
    }

    public void notifyOrderCreation(OrderResponse response) throws ApiException {
        makeRequest(HttpMethod.POST, "notifications/order-creation", null, response, Void.class);
    }

}
