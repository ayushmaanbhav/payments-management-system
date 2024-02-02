package com.ayushmaanbhav.service.commons.exception;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import lombok.NonNull;

public class RetryableException extends ApiException {
    public RetryableException(@NonNull String message, @NonNull ErrorCode errorCode) {
        super(message, errorCode);
    }
}
