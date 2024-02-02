package com.ayushmaanbhav.commonsspring.requestMetadata;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;

import java.util.*;
import java.util.stream.Collectors;
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RequestMetadata {
    public static Set<Header> HEADERS = Arrays.stream(Header.values()).collect(Collectors.toSet());
    public static Set<String> EXCLUDED_PATHS = Set.of("/webhook");

    @Getter
    public enum Header {
        CORRELATION_ID("correlationId", true);

        private final String headerName;
        private final boolean required;
        Header(@NonNull String headerName, boolean required) {
            this.headerName = headerName;
            this.required = required;
        }
    }

    public static @NonNull Map<String, String> getValues(@NonNull List<Header> headers) throws ApiException {
        Map<String, String> headerValues = new HashMap<>();
        for (Header header : headers) {
            var value = ThreadContext.get(header.getHeaderName());
            if (value == null && header.isRequired()) {
                log.error("Header value is required: " + header.getHeaderName());
                throw new ApiException("header value is required: " + header.getHeaderName(),
                        ErrorCode.INTERNAL_SERVER_ERROR);
            }
            headerValues.put(header.getHeaderName(), value);
        }
        return headerValues;
    }

    public static @NonNull String getValue(@NonNull Header header) throws ApiException {
        return getValues(List.of(header)).get(header.getHeaderName());
    }

    public static void setValue(@NonNull Header header, @NonNull String value) {
        ThreadContext.put(header.getHeaderName(), value);
    }

    public static void clear(Header header) {
        ThreadContext.remove(header.getHeaderName());
    }
}
