package com.ayushmaanbhav.ledger.exception;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;

public class TransactionValidationException extends ApiException {
    public TransactionValidationException(String message) {
        super(message, ErrorCode.BAD_REQUEST);
    }
}
