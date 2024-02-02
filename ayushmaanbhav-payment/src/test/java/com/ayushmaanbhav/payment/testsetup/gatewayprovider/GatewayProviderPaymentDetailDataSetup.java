package com.ayushmaanbhav.payment.testsetup.gatewayprovider;

import com.ayushmaanbhav.commons.contstants.PaymentType;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderPaymentDetail;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import static com.ayushmaanbhav.payment.util.TestValues.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GatewayProviderPaymentDetailDataSetup {

    public static GatewayProviderPaymentDetail getGatewayProviderPaymentDetail() {
        return GatewayProviderPaymentDetail.builder()
                .orderId(LINE_ITEM_EXTERNAL_ID)
                .providerOrderId(PROVIDER_ORDER_ID)
                .type(PaymentType.PAYMENT)
                .paymentWebUrl(WEB_URL)
                .paymentDeepLink(DEEP_LINK)
                .build();
    }
}
