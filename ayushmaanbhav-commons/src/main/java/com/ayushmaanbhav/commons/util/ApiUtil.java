package com.ayushmaanbhav.commons.util;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;

public class ApiUtil {
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

    public static void verifyTruth(boolean result, String message) throws ApiException {
        if (!result) {
            throw new ApiException(message, ErrorCode.NOT_FOUND);
        }
    }
}
