package com.ayushmaanbhav.gatewayProviderApiClient.genericHttpClient.dto;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private String code;
    private String type;
}
