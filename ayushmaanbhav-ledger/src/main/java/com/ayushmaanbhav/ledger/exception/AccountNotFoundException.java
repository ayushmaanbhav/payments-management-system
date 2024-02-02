package com.ayushmaanbhav.ledger.exception;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;

public class AccountNotFoundException extends ApiException {
    public AccountNotFoundException(String message) {
        super(message, ErrorCode.NOT_FOUND);
    }
}
