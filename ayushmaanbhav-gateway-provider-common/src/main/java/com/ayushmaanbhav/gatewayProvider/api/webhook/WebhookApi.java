package com.ayushmaanbhav.gatewayProvider.api.webhook;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.gatewayProvider.embedded.ApiEventDetail;
import lombok.NonNull;

public interface WebhookApi<Req, Res> {

    @NonNull Res process(@NonNull WebhookRequest<Req> request) throws ApiException;

    void saveEventApiDetail(@NonNull ApiEventDetail apiDetail);
}
