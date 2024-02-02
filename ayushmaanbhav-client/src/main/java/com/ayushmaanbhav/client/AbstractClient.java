package com.ayushmaanbhav.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import com.ayushmaanbhav.commonsspring.requestMetadata.RequestMetadata;
import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import com.ayushmaanbhav.commons.exception.ErrorData;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.ayushmaanbhav.commons.contstants.StringConstants.SecurityConstants.API_KEY_HEADER_NAME;

public abstract class AbstractClient {

    protected RestTemplate restTemplate;
    protected HttpHeaders baseHeaders;
    protected ObjectMapper mapper;
    protected String baseUrl;

    public AbstractClient(String baseUrl, HttpHeaders baseHeaders) {
        this.baseUrl = baseUrl;
        this.baseHeaders = baseHeaders;
        this.mapper = getMapper();
        this.restTemplate = new RestTemplateFactory().getRestTemplate();
    }

    public AbstractClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.baseHeaders = new HttpHeaders();
        this.mapper = getMapper();
        this.restTemplate = new RestTemplateFactory().getRestTemplate();
    }

    public AbstractClient(String baseUrl, String apiKey) {
        this.baseUrl = baseUrl;
        HttpHeaders baseHeaders = new HttpHeaders();
        baseHeaders.add(API_KEY_HEADER_NAME, apiKey);
        this.baseHeaders = baseHeaders;
        this.mapper = getMapper();
        this.restTemplate = new RestTemplateFactory().getRestTemplate();
    }

    protected <T, R> R makeRequest(HttpMethod method, String path, HttpHeaders header, T request, Class<R> recieved) throws ApiException {
        return makeRequestInternal(method, path, header, new LinkedMultiValueMap<>(), request, recieved);
    }

    protected <T, R> R makeRequest(HttpMethod method, String path, HttpHeaders header,
                                   MultiValueMap<String, String> params, T request, Class<R> recieved) throws ApiException {
        return makeRequestInternal(method, path, header, params, request, recieved);
    }

    protected <T, R> R makeRequest(HttpMethod method, String baseUrl, String path, HttpHeaders header,
                                   MultiValueMap<String, String> params, T request, Class<R> recieved) throws ApiException {
        return makeRequestInternal(method, baseUrl, path, header, params, request, recieved);
    }

    private <T, R> R makeRequestInternal(HttpMethod method, String path, HttpHeaders headers,
                                         MultiValueMap<String, String> params, T request, Class<R> recieved) throws ApiException {
        String fullUrl = getFullUrlWithParams(path, params);
        HttpHeaders allHeaders = getHeaders();
        allHeaders.setContentType(MediaType.APPLICATION_JSON);
        allHeaders.setAll(RequestMetadata.getValues(List.of(RequestMetadata.Header.CORRELATION_ID)));
        if (headers != null) {
            allHeaders.putAll(headers);
        }
        HttpEntity<?> payload = new HttpEntity<>(request, allHeaders);
        return makeRequestInternalFinal(method, fullUrl, payload, recieved);
    }

    private <T, R> R makeRequestInternal(HttpMethod method,String baseUrl, String relativeUrl, HttpHeaders headers,
                                         MultiValueMap<String, String> params, T request, Class<R> recieved) throws ApiException {
        String fullUrlWithParams = getUrlWithParams(params, getFullUrl(baseUrl, relativeUrl));
        HttpHeaders allHeaders = getHeaders();
        allHeaders.setContentType(MediaType.APPLICATION_JSON);
        allHeaders.setAll(RequestMetadata.getValues(List.of(RequestMetadata.Header.CORRELATION_ID)));
        if (headers != null) {
            allHeaders.putAll(headers);
        }
        HttpEntity<?> payload = new HttpEntity<>(request, allHeaders);
        return makeRequestInternalFinal(method, fullUrlWithParams, payload, recieved);
    }

    private <T, R> R makeRequestInternalFinal(HttpMethod method, String fullUrl, HttpEntity<T> payload,
                                              Class<R> received) throws ApiException {
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(fullUrl, method, payload, String.class);
        } catch (RestClientException t) {
            throw new ApiException("A remote IO error has occurred", ErrorCode.INTERNAL_SERVER_ERROR);
        }
        String responseBody = response.getBody();
        try {
            HttpStatus status = response.getStatusCode();
            if (status.is2xxSuccessful() && responseBody != null) {
                return received.equals(Void.class) ? null : mapper.readValue(responseBody, received);
            } else if (status.is2xxSuccessful()) {
                return null;
            } else {
                ErrorData error = mapper.readValue(responseBody, ErrorData.class);
                throw new ApiException(error.getMessage(), error.getCode());
            }
        } catch (IOException e) {
            throw new ApiException("Error parsing remote response", ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private static ObjectMapper getMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Configuring for parsing date and time
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(ZonedDateTime.class,
                new ZonedDateTimeSerializer(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        javaTimeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
        objectMapper.registerModule(javaTimeModule);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objectMapper;
    }

    protected String getFullUrlWithParams(String path, MultiValueMap<String, String> params) {
        String fullUrl = getFullUrl(path);
        return getUrlWithParams(params, fullUrl);
    }

    private String getUrlWithParams(MultiValueMap<String, String> params, String fullUrl) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(fullUrl).queryParams(params);
        fullUrl = builder.buildAndExpand().toUriString();
        return fullUrl;
    }

    protected String getFullUrl(String s) {
        return this.baseUrl + s;
    }

    protected HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        if (baseHeaders != null) {
            headers.putAll(baseHeaders);
        }
        return headers;
    }

    private String getFullUrl(String baseUrl, String path) {
        return baseUrl + path;
    }

}
