package com.ayushmaanbhav.ledger.util;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;

public class Util {
    public static void verifyNonExistence(Object obj, String message) throws ApiException {
        if (obj != null) {
            throw new ApiException(message, ErrorCode.CONFLICT);
        }
    }

    public static void verifyExistence(Object obj, String message) throws ApiException {
        if (obj == null) {
            throw new ApiException(message, ErrorCode.NOT_FOUND);
        }
    }
}
