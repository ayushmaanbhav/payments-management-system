package com.ayushmaanbhav.gatewayProvider.testsetup.paymentDto;

import com.ayushmaanbhav.commons.contstants.Currency;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.CreatePaymentRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;

import static com.ayushmaanbhav.gatewayProvider.testsetup.util.TestValues.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CreatePaymentRequestDataSetup {

    public static CreatePaymentRequest getCreatePaymentRequest() {
        return CreatePaymentRequest.builder()
                .orderId(LINE_ITEM_EXTERNAL_ID)
                .gatewayProviderConfigId(PROVIDER_CONFIG_EXTERNAL_ID)
                .normalisedAmount(NORMALISED_AMOUNT)
                .currency(Currency.INR)
                .expiresInSecond(LINK_EXPIRY_TIME)
                .customerPhone(PHONE)
                .params(new HashMap<>())
                .build();
    }
}
