package com.ayushmaanbhav.commons.exception;

import lombok.Getter;
import lombok.Setter;

public class ApiException extends Exception{

    public ApiException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    @Getter
    @Setter
    private ErrorCode errorCode;

}
