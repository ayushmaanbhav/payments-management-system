package com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ayushmaanbhav.commons.contstants.ContentType;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import com.ayushmaanbhav.gatewayProvider.constant.ApiEventType;
import com.ayushmaanbhav.gatewayProvider.embedded.ApiEventDetail;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebhookEventApiDetailMapper {
    ObjectMapper mapper;

    public @NonNull ApiEventDetail forward(
            @NonNull Map<String, String> headers, @NonNull Map<String, String> reqParams, @NonNull String requestString,
            String eventType, ApiException apiException
    ) throws ApiException {
        String httpStatus = String.valueOf(HttpStatus.OK.value());
        if (apiException != null) {
            httpStatus = apiException.getErrorCode().name();
        }
        return ApiEventDetail.builder()
                .event(eventType)
                .eventType(ApiEventType.HTTP_WEBHOOK)
                .httpMethod(HttpMethod.POST.name())
                .contentType(ContentType.JSON)
                .request(requestString)
                .headers(mapperWriteValue(headers))
                .queryParams(mapperWriteValue(reqParams))
                .httpStatusCode(httpStatus)
                .build();
    }

    private <T> String mapperWriteValue(T value) throws ApiException {
        try{
            return mapper.writeValueAsString(value);
        } catch (IOException e) {
            throw new ApiException("Error writing value using objectmapper", ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
