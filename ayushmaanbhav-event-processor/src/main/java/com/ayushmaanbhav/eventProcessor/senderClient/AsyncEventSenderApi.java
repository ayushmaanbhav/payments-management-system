package com.ayushmaanbhav.eventProcessor.senderClient;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import com.ayushmaanbhav.commons.request.AsyncEventRequest;
import org.springframework.scheduling.annotation.Async;

public interface AsyncEventSenderApi {

    @Async("commons-executor")
    default void send(AsyncEventRequest request) throws ApiException {
        throw new ApiException("Method not implemented", ErrorCode.BAD_REQUEST);
    }

}
