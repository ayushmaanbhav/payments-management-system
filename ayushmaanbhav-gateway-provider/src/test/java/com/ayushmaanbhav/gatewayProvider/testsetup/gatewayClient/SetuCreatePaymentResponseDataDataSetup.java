package com.ayushmaanbhav.gatewayProvider.testsetup.gatewayClient;

import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto.SetuCreatePaymentResponseData;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto.SetuPaymentLink;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import static com.ayushmaanbhav.gatewayProvider.testsetup.util.TestValues.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SetuCreatePaymentResponseDataDataSetup {

    public static SetuCreatePaymentResponseData getSetuCreatePaymentResponseData() {
        return SetuCreatePaymentResponseData.builder()
                .providerOrderId(PROVIDER_ORDER_ID)
                .paymentLink(SetuPaymentLink.builder()
                        .shortUrl(WEB_URL)
                        .upiLink(DEEP_LINK)
                        .upiId("testUpiId")
                        .build())
                .build();
    }
}
