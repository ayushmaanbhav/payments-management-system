package com.ayushmaanbhav.gatewayProviderApiClient.genericHttpClient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.ayushmaanbhav.commons.util.ApiUtil;
import com.ayushmaanbhav.commonsspring.requestMetadata.RequestMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ayushmaanbhav.client.RestTemplateFactory;
import com.ayushmaanbhav.gatewayProvider.exception.GatewayGenericApiException;
import com.ayushmaanbhav.gatewayProvider.exception.GatewayGenericApiInternalException;
import com.ayushmaanbhav.gatewayProvider.constant.ApiEventType;
import com.ayushmaanbhav.commons.contstants.ContentType;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import com.ayushmaanbhav.gatewayProvider.embedded.ApiEventDetail;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayGenericResponse;
import com.ayushmaanbhav.gatewayProviderApiClient.genericHttpClient.dto.ResponseDetail;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Log4j2
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GatewayClient {
    RestTemplate restTemplate = new RestTemplateFactory().getRestTemplate();
    ObjectMapper mapper;


    public <T, R, ER> @NonNull GatewayGenericResponse<R> makeGatewayRequest(
            @NonNull HttpMethod method, @NonNull String baseUrl, @NonNull String path, @NonNull HttpHeaders headers,
            @NonNull MultiValueMap<String, String> params, T request, @NonNull TypeReference<R> responseType,
            @NonNull TypeReference<ER> errorResponseType, @NonNull Function<ER, ErrorCode> errorConverter,
            @NonNull Function<ER, String> errorMessageRetriever
    ) throws ApiException {
        log.info("Calling Generic Gateway Client");
        String fullUrl = getFullUrl(baseUrl, path);
        ApiEventDetail.ApiEventDetailBuilder eventDetailBuilder = ApiEventDetail.builder()
                .event(path).eventType(ApiEventType.HTTP_CLIENT_CALL)
                .httpMethod(method.name()).contentType(ContentType.JSON)
                .request(mapperWriteValue(request))
                .headers(mapperWriteValue(headers))
                .queryParams(mapperWriteValue(params));

        ResponseDetail<R> response;
        try {
            response = makeGatewayRequestInternal(method, fullUrl, headers, params, request, responseType,
                    errorResponseType, errorConverter, errorMessageRetriever);
        } catch (GatewayGenericApiInternalException e) {
            log.error("Error Code: " + e.getErrorCode().name() + ", Error Message: " + e.getMessage(), e);
            throw new GatewayGenericApiException(e.getMessage(), e.getErrorCode(), eventDetailBuilder
                    .response(e.getResponse()).httpStatusCode(e.getHttpStatusCode()).build());
        }
        return GatewayGenericResponse.<R>builder()
                .pojo(response.getResponse())
                .rawDetail(eventDetailBuilder
                        .response(response.getRawResponse())
                        .httpStatusCode(response.getHttpStatusCode()).build())
                .build();
    }

    private <T, R, ER> ResponseDetail<R> makeGatewayRequestInternal(
            HttpMethod method, String fullUrl1, HttpHeaders headers,
            MultiValueMap<String, String> params, T request, TypeReference<R> responseType, TypeReference<ER> errorResponseType,
            Function<ER, ErrorCode> errorConverter, Function<ER, String> errorMessageRetriever) throws ApiException {
        String fullUrl = getFullUrlWithParams(fullUrl1, params);
        HttpHeaders allHeaders = new HttpHeaders();
        allHeaders.setContentType(MediaType.APPLICATION_JSON);
        allHeaders.setAll(RequestMetadata.getValues(List.of(RequestMetadata.Header.CORRELATION_ID)));
        if (headers != null) {
            allHeaders.putAll(headers);
        }
        HttpEntity<?> payload = new HttpEntity<>(mapperWriteValue(request), allHeaders);
        return makeGatewayRequestFinal(method, fullUrl, payload, responseType, errorResponseType, errorConverter,
                errorMessageRetriever);
    }

    private String getFullUrlWithParams(String fullUrl, MultiValueMap<String, String> params) {
        return getUrlWithParams(params, fullUrl);
    }

    private String getUrlWithParams(MultiValueMap<String, String> params, String fullUrl) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(fullUrl).queryParams(params);
        fullUrl = builder.buildAndExpand().toUriString();
        return fullUrl;
    }

    private String getFullUrl(String baseUrl, String path) {
        int slashCount = (baseUrl.endsWith("/") ? 1: 0) + (path.startsWith("/") ? 1: 0);
        if (slashCount == 1) {
            return baseUrl + path;
        } else if (slashCount == 2) {
            return baseUrl + path.substring(1);
        } else {
            return baseUrl + "/" + path;
        }
    }

    private <T, R, ER> ResponseDetail<R> makeGatewayRequestFinal(
            HttpMethod method, String fullUrl, HttpEntity<T> payload, TypeReference<R> received, TypeReference<ER> errorResponseType,
            Function<ER, ErrorCode> errorConverter, Function<ER, String> errorMessageRetriever) throws ApiException {
        HttpStatus status;
        String responseBody;
        MediaType contentType;
        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(fullUrl, method, payload, String.class);
            status = response.getStatusCode();
            contentType = response.getHeaders().getContentType();
            responseBody = response.getBody();
        } catch (RestClientResponseException e) {
            status = HttpStatus.valueOf(e.getRawStatusCode());
            contentType = Optional.ofNullable(e.getResponseHeaders())
                    .map(HttpHeaders::getContentType).orElse(null);
            responseBody = e.getResponseBodyAsString();
        }
        // following try catch for internal status is only for setu, need to refactor it later
        if (MediaType.APPLICATION_JSON.equals(contentType)) {
            try {
                InternalStatusPojo internalStatus = mapperReadValue(responseBody, new TypeReference<>() {});
                if (internalStatus.getStatus() != null) {
                    status = HttpStatus.valueOf(internalStatus.getStatus());
                }
            } catch (ApiException | IllegalArgumentException e) {
                log.info("No internal http status found in response body : {}", e.getMessage());
            }
        }
        if (status.is2xxSuccessful() && responseBody != null) {
            if (received.equals(new TypeReference<Void>() {})) {
                log.info("Response is of type Void");
                return new ResponseDetail<>(null, String.valueOf(status.value()), responseBody);
            } else {
                log.info("Response is of type {}", received.getType().getTypeName());
                R responsePojo;
                try {
                    ApiUtil.verifyTruth(MediaType.APPLICATION_JSON.equals(contentType),
                            "invalid response type found " + contentType);
                    responsePojo = mapperReadValue(responseBody, received);
                } catch (ApiException e) {
                    throw new GatewayGenericApiInternalException(e.getMessage(), e.getErrorCode(),
                            String.valueOf(status.value()), responseBody);
                }
                return new ResponseDetail<>(responsePojo, String.valueOf(status.value()), responseBody);
            }
        } else if (status.is2xxSuccessful()) {
            log.info("API call is successful and Response is empty");
            return new ResponseDetail<>(null, String.valueOf(status.value()), responseBody);
        } else if (MediaType.APPLICATION_JSON.equals(contentType)) {
            log.info("API call failed status : {}", status.value());
            ER error;
            try {
                error = mapperReadValue(responseBody, errorResponseType);
            } catch (ApiException e) {
                throw new GatewayGenericApiInternalException(e.getMessage(), e.getErrorCode(),
                        String.valueOf(status.value()), responseBody);
            }
            throw convert(error, String.valueOf(status.value()), responseBody, errorConverter, errorMessageRetriever);
        } else {
            log.info("API call failed status : {}", status.value());
            throw new GatewayGenericApiInternalException(null, errorCodeMap.get(status.value()),
                    String.valueOf(status.value()), responseBody);
        }
    }

    private <ER> ApiException convert(
            ER errorResponse, String httpStatusCode, String responseBody, Function<ER, ErrorCode> errorConverter,
            Function<ER, String> errorMessageRetriever) {
        log.info("Converting http status and response into internal error code {}", httpStatusCode);
        ErrorCode errorCode = errorConverter.apply(errorResponse);
        String errorMessage = errorMessageRetriever.apply(errorResponse);
        log.info("Converted error code: " + errorCode.name() + ", error Message: " + errorMessage);
        return new GatewayGenericApiInternalException(errorMessage, errorCode, httpStatusCode, responseBody);
    }

    private <R> R mapperReadValue(String value, TypeReference<R> received) throws ApiException {
        try{
            return mapper.readValue(value, received);
        } catch (IOException e) {
            log.error("Error parsing remote response", e);
            throw new ApiException("Error parsing remote response", ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private <T> String mapperWriteValue(T value) throws ApiException {
        try{
            return mapper.writeValueAsString(value);
        } catch (IOException e) {
            log.error("Error writing value using objectmapper", e);
            throw new ApiException("Error writing value using objectmapper", ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class InternalStatusPojo {
        private Integer status;
    }

    private static Map<Integer, ErrorCode> errorCodeMap = new HashMap<>() {{
        put(400, ErrorCode.BAD_REQUEST);
        put(401, ErrorCode.UNAUTHORISED);
        put(403, ErrorCode.FORBIDDEN);
        put(405, ErrorCode.BAD_REQUEST);
        put(429, ErrorCode.TOO_MANY_REQUESTS);
        put(500, ErrorCode.INTERNAL_SERVER_ERROR);
        put(503, ErrorCode.INTERNAL_SERVER_ERROR);
    }};
}
