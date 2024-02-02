package com.ayushmaanbhav.commonsspring.requestMetadata;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RequestMetadataHandler implements HandlerInterceptor {
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        for (RequestMetadata.Header header : RequestMetadata.HEADERS) {
            var headerValue = request.getHeader(header.getHeaderName());
            if (headerValue != null) {
                ThreadContext.put(header.getHeaderName(), headerValue);
            } else if (header.isRequired() && !isPathExcluded(path)) {
                throw new ApiException("request metadata header is required: " + header.getHeaderName(),
                        ErrorCode.BAD_REQUEST);
            }
        }
        return true;
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                           @NonNull Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        for (RequestMetadata.Header header : RequestMetadata.HEADERS) {
            ThreadContext.remove(header.getHeaderName());
        }
    }

    private boolean isPathExcluded(String path) {
        return RequestMetadata.EXCLUDED_PATHS.stream().anyMatch(path::startsWith);
    }
}
