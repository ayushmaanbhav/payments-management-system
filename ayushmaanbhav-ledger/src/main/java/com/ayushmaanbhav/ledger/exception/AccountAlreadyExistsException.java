package com.ayushmaanbhav.ledger.exception;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;

public class AccountAlreadyExistsException extends ApiException {
    public AccountAlreadyExistsException(String message) {
        super(message, ErrorCode.CONFLICT);
    }
}
