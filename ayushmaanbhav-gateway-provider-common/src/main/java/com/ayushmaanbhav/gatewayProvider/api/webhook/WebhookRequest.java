package com.ayushmaanbhav.gatewayProvider.api.webhook;

import com.ayushmaanbhav.commons.contstants.GatewayProvider;
import com.ayushmaanbhav.gatewayProvider.embedded.ApiEventDetail;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.ZonedDateTime;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Jacksonized
public class WebhookRequest<Data> {
    @NonNull String eventId;
    @NonNull String eventName;
    @NonNull GatewayProvider provider;
    @NonNull ZonedDateTime eventDate;
    @NonNull Data data;

    @NonNull ApiEventDetail apiEventDetail;
}
