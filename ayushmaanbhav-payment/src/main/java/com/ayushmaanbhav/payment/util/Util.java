package com.ayushmaanbhav.payment.util;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Util {
    public static void verifyNonExistence(Object obj, String message) throws ApiException {
        if (obj != null) {
            log.error("Error Message: " + message);
            throw new ApiException(message, ErrorCode.CONFLICT);
        }
    }

    public static void verifyExistence(Object obj, String message) throws ApiException {
        if (obj == null) {
            log.error("Error Message: " + message);
            throw new ApiException(message, ErrorCode.NOT_FOUND);
        }
    }

    public static void verifyTruth(boolean result, String message) throws ApiException {
        if (!result) {
            log.error("Error Message: " + message);
            throw new ApiException(message, ErrorCode.NOT_FOUND);
        }
    }
}
