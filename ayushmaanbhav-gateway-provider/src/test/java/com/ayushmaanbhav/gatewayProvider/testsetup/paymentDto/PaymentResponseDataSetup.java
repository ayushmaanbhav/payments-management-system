package com.ayushmaanbhav.gatewayProvider.testsetup.paymentDto;

import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.PaymentResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import static com.ayushmaanbhav.gatewayProvider.testsetup.util.TestValues.*;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentResponseDataSetup {

    public static PaymentResponse getPaymentResponse(PaymentStatus status) {
        return PaymentResponse.builder()
                .orderId(LINE_ITEM_EXTERNAL_ID)
                .providerOrderId(PROVIDER_ORDER_ID)
                .paymentWebUrl(WEB_URL)
                .paymentDeepLink(DEEP_LINK)
                .status(status)
                .providerStatus(PROVIDER_STATUS)
                .build();
    }
}
