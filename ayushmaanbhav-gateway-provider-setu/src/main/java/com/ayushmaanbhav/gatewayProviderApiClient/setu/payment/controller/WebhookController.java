package com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ayushmaanbhav.commons.contstants.GatewayProvider;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import com.ayushmaanbhav.commons.util.LUUID;
import com.ayushmaanbhav.commonsspring.requestMetadata.RequestMetadata;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.PaymentResponse;
import com.ayushmaanbhav.gatewayProvider.api.webhook.WebhookApi;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.constant.SetuEventType;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto.WebhookRequest;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.mapper.WebhookEventApiDetailMapper;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.mapper.WebhookPaymentResponseMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;



@RestController
@RequestMapping("webhook/setu/payment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
public class WebhookController {
    ObjectMapper objectMapper;
    WebhookPaymentResponseMapper paymentMapper;
    WebhookEventApiDetailMapper eventApiDetailMapper;
    WebhookApi<PaymentResponse, Void> webhookApi;

    @PostMapping("/notifications")
    public void processPaymentWebhook(
            @RequestHeader Map<String, String> headers, @RequestParam Map<String, String> reqParams,
            @RequestBody String requestString
    ) throws ApiException {
        RequestMetadata.setValue(RequestMetadata.Header.CORRELATION_ID, LUUID.generateUuid());
        log.info("setu webhook received {} {} {}", headers, reqParams, requestString);
        WebhookRequest request = null;
        try {
            request = objectMapper.readValue(requestString, WebhookRequest.class);
        } catch (JsonProcessingException e) {
            log.error("error parsing setu payment notification request", e);
            var apiEx = new ApiException("error while parsing request", ErrorCode.INTERNAL_SERVER_ERROR);
            var apiDetail = eventApiDetailMapper.forward(headers, reqParams, requestString, null, apiEx);
            webhookApi.saveEventApiDetail(apiDetail);
        }
        if (request != null && !CollectionUtils.isEmpty(request.getEvents())) {
            for (var event : request.getEvents()) {
                try {
                    processEvent(event, headers, reqParams, requestString);
                } catch (ApiException e) {
                    log.error("error parsing setu payment notification request " + event.getId(), e);
                    var apiDetail = eventApiDetailMapper.forward(headers, reqParams, requestString, null, e);
                    webhookApi.saveEventApiDetail(apiDetail);
                }
            }
        }

    }

    private void processEvent(
            WebhookRequest.Event event,
            Map<String, String> headers, Map<String, String> reqParam, String requestString
    ) throws ApiException {
        if (SetuEventType.BILL_FULFILMENT_STATUS.name().equals(event.getType())) {
            var paymentResponse = paymentMapper.forward(event);
            var apiEventDetail = eventApiDetailMapper
                    .forward(headers, reqParam, requestString, event.getType(), null);
            webhookApi.process(com.ayushmaanbhav.gatewayProvider.api.webhook.WebhookRequest.<PaymentResponse>builder()
                    .eventId(event.getId())
                    .eventName(event.getType())
                    .provider(GatewayProvider.SETU)
                    .data(paymentResponse)
                    .eventDate(event.getTimeStamp())
                    .apiEventDetail(apiEventDetail)
                    .build());
        } else {
            log.info("got unsupported event type from setu notification: " + event.getType());
        }
    }
}
