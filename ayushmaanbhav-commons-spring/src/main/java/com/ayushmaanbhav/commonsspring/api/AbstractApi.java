package com.ayushmaanbhav.commonsspring.api;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;

public abstract class AbstractApi {

    protected void verifyNonExistence(Object obj, String message) throws ApiException {
        if (obj != null) {
            throw new ApiException(message, ErrorCode.CONFLICT);
        }
    }

    protected void verifyExistence(Object obj, String message) throws ApiException {
        if (obj == null) {
            throw new ApiException(message, ErrorCode.NOT_FOUND);
        }
    }

}
