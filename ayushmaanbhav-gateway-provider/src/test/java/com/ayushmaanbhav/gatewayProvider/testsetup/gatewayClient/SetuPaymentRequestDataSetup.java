package com.ayushmaanbhav.gatewayProvider.testsetup.gatewayClient;

import com.ayushmaanbhav.commons.contstants.Currency;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto.SetuAmount;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto.SetuPaymentRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.ayushmaanbhav.gatewayProvider.testsetup.util.TestValues.*;
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SetuPaymentRequestDataSetup {

    static String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static SetuPaymentRequest getSetuPaymentRequest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        SetuAmount setuAmount = SetuAmount.builder()
                .normalisedAmount(NORMALISED_AMOUNT)
                .currency(Currency.INR)
                .build();
        String expiryDate = ZonedDateTime.now().plusSeconds(LINK_EXPIRY_TIME).format(formatter);
        return  SetuPaymentRequest.builder()
                .orderId(LINE_ITEM_EXTERNAL_ID)
                .amount(setuAmount)
                .expiryDate(expiryDate)
                .amountExactness("EXACT")
                .build();
    }


}
