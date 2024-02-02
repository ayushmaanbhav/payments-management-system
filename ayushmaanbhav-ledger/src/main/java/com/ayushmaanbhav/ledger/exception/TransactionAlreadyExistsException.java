package com.ayushmaanbhav.ledger.exception;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;

public class TransactionAlreadyExistsException extends ApiException {
    public TransactionAlreadyExistsException(String message) {
        super(message, ErrorCode.CONFLICT);
    }
}
