package com.ayushmaanbhav.ledger.exception;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;

public class InsufficientFundsException extends ApiException {
    public InsufficientFundsException(String message) {
        super(message, ErrorCode.BAD_REQUEST);
    }
}
