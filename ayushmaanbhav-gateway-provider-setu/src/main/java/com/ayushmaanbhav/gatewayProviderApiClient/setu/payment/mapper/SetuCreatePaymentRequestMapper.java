package com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.mapper;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.mapper.OneWayMapper;
import com.ayushmaanbhav.gatewayProvider.api.payment.dto.CreatePaymentRequest;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto.SetuAmount;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto.SetuPaymentRequest;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SetuCreatePaymentRequestMapper implements OneWayMapper<CreatePaymentRequest, SetuPaymentRequest> {
    static String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @Override
    public @NonNull SetuPaymentRequest forward(@NonNull CreatePaymentRequest input) throws ApiException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        SetuAmount setuAmount = SetuAmount.builder()
                .normalisedAmount(input.getNormalisedAmount())
                .currency(input.getCurrency())
                .build();
        String expiryDate = ZonedDateTime.now().plusSeconds(input.getExpiresInSecond()).format(formatter);
        return  SetuPaymentRequest.builder()
                .orderId(input.getOrderId())
                .amount(setuAmount)
                .expiryDate(expiryDate)
                .amountExactness("EXACT")
                .build();
    }
}
