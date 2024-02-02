package com.ayushmaanbhav.gatewayProvider.exception;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import com.ayushmaanbhav.gatewayProvider.embedded.ApiEventDetail;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GatewayGenericApiInternalException extends ApiException {
    String response;
    String httpStatusCode;

    public GatewayGenericApiInternalException(String message, ErrorCode errorCode, String httpStatusCode, String response) {
        super(message, errorCode);
        this.response = response;
        this.httpStatusCode = httpStatusCode;
    }
}
