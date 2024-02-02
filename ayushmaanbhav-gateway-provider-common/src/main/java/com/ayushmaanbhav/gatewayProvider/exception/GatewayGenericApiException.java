package com.ayushmaanbhav.gatewayProvider.exception;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import com.ayushmaanbhav.gatewayProvider.embedded.ApiEventDetail;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GatewayGenericApiException extends ApiException {
    ApiEventDetail apiEventDetail;

    public GatewayGenericApiException(String message, ErrorCode errorCode, @NonNull ApiEventDetail apiEventDetail) {
        super(message, errorCode);
        this.apiEventDetail = apiEventDetail;
    }
}
