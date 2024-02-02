package com.ayushmaanbhav.payment.testsetup.dto;

import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.constant.SetuPaymentStatus;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.PaymentResponse;

import static com.ayushmaanbhav.payment.util.TestValues.*;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentResponseDataSetup {

    public static PaymentResponse getPaymentResponse(PaymentStatus status) {
        return PaymentResponse.builder()
                .orderId(LINE_ITEM_EXTERNAL_ID)
                .providerOrderId(PROVIDER_ORDER_ID)
                .normalisedPaidAmount(NORMALISED_AMOUNT)
                .paymentWebUrl(WEB_URL)
                .paymentDeepLink(DEEP_LINK)
                .status(status)
                .providerStatus("BILL_CREATED")
                .build();
    }
}
