package com.ayushmaanbhav.commons.exception;

import lombok.Data;

@Data
public class ErrorData {

    private ErrorCode code;
    private String message;
    private String description;

}
