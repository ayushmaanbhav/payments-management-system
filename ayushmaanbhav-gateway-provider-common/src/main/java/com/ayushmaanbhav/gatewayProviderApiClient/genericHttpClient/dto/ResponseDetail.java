package com.ayushmaanbhav.gatewayProviderApiClient.genericHttpClient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDetail<T> {
    private final T response;
    private final String httpStatusCode;
    private final String rawResponse;
}
