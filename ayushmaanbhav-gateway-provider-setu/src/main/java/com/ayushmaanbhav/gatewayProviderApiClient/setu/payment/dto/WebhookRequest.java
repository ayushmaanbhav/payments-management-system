package com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto;

import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.constant.SetuPaymentStatus;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.ZonedDateTime;
import java.util.List;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Jacksonized
public class WebhookRequest {
    @NonNull PartnerDetail partnerDetails;
    @NonNull List<Event> events;

    @Value
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(toBuilder = true)
    @Jacksonized
    public static class PartnerDetail {
        @NonNull String appID;
        @NonNull String productInstanceID;
    }

    @Value
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(toBuilder = true)
    @Jacksonized
    public static class Event {
        @NonNull String id;
        @NonNull String type;
        @NonNull ZonedDateTime timeStamp;
        @NonNull EventData data;
    }

    @Value
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(toBuilder = true)
    @Jacksonized
    public static class EventData {
        @NonNull AmountPaid amountPaid;
        @NonNull String billerBillID;
        @NonNull String platformBillID;
        @NonNull SetuPaymentStatus status;
        String amountExactness;
        AdditionalInfo additionalInfo;
        String payerVpa;
        String transactionId;
        String transactionNote;
    }

    @Value
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(toBuilder = true)
    @Jacksonized
    public static class AmountPaid {
        @NonNull String currencyCode;
        @NonNull Long value;
    }

    @Value
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder(toBuilder = true)
    @Jacksonized
    public static class AdditionalInfo {
        @NonNull String UUID;
        @NonNull String tags;
    }
}
