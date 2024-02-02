package com.ayushmaanbhav.service.commons.exception;

import com.ayushmaanbhav.service.commons.model.response.ErrorDetail;
import lombok.NonNull;

import java.util.List;

public class ValidatorException extends Exception {
    private final int code;
    private final List<ErrorDetail> errors;

    public final int getCode() {
        return this.code;
    }

    public final List<ErrorDetail> getErrors() {
        return this.errors;
    }

    private ValidatorException(int code, String message, Throwable throwable, List<ErrorDetail> errors) {
        super(message, throwable);
        this.code = code;
        this.errors = errors;
    }

    public ValidatorException(int code, @NonNull String message) {
        this(code, message, null, null);
    }

    public ValidatorException(int code, @NonNull List<ErrorDetail> errors) {
        this(code, null, null, errors);
    }

    public ValidatorException(int code, @NonNull String message, @NonNull List<ErrorDetail> errors) {
        this(code, message, null, errors);
    }

    public ValidatorException(int code, @NonNull String message, @NonNull Throwable throwable) {
        this(code, message, throwable, null);
    }
}
