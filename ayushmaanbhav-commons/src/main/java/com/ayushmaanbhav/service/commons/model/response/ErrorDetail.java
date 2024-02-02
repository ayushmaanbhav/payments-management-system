package com.ayushmaanbhav.service.commons.model.response;

import java.util.Map;

public final class ErrorDetail {
    private final String code;
    private final String message;
    private final Map<String, String> params;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public ErrorDetail(String code, String message, Map<String, String> params) {
        this.code = code;
        this.message = message;
        this.params = params;
    }
}
