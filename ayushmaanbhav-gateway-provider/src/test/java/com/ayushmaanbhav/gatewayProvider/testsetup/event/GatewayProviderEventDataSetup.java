package com.ayushmaanbhav.gatewayProvider.testsetup.event;

import com.ayushmaanbhav.commons.contstants.ContentType;
import com.ayushmaanbhav.gatewayProvider.constant.ApiEventType;
import com.ayushmaanbhav.gatewayProvider.embedded.ApiEventDetail;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderEvent;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import static com.ayushmaanbhav.gatewayProvider.testsetup.util.TestValues.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GatewayProviderEventDataSetup {

    public static GatewayProviderEvent getGatewayProviderEvent() {

        ApiEventDetail apiEventDetail = ApiEventDetail.builder()
                .eventType(ApiEventType.HTTP_CLIENT_CALL)
                .event("payment-links")
                .httpMethod("POST")
                .headers(HEADERS_STRING)
                .queryParams("{}")
                .request(REQUEST_STRING)
                .httpStatusCode("200")
                .response(RESPONSE_STRING)
                .contentType(ContentType.JSON)
                .build();
        return GatewayProviderEvent.builder()
                .orderId(LINE_ITEM_EXTERNAL_ID)
                .apiClientClass(API_CLIENT_CLASS)
                .providerOrderId(PROVIDER_ORDER_ID)
                .providerStatus(PROVIDER_STATUS)
                .rawDetail(apiEventDetail)
                .build();
    }
}
