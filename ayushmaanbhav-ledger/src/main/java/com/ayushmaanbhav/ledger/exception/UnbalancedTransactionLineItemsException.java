package com.ayushmaanbhav.ledger.exception;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;

public class UnbalancedTransactionLineItemsException extends ApiException {
    public UnbalancedTransactionLineItemsException(String message) {
        super(message, ErrorCode.BAD_REQUEST);
    }
}
