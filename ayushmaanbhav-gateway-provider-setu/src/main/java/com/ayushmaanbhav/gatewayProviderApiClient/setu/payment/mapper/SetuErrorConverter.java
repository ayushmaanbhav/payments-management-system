package com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.mapper;

import com.ayushmaanbhav.commons.exception.ErrorCode;
import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto.error.SetuErrorResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SetuErrorConverter implements Function<SetuErrorResponse, ErrorCode> {

    Map<Integer, ErrorCode> errorCodeMap = new HashMap<>() {{
       put(400, ErrorCode.BAD_REQUEST);
       put(401, ErrorCode.UNAUTHORISED);
       put(403, ErrorCode.FORBIDDEN);
       put(405, ErrorCode.BAD_REQUEST);
       put(429, ErrorCode.TOO_MANY_REQUESTS);
       put(500, ErrorCode.INTERNAL_SERVER_ERROR);
       put(503, ErrorCode.INTERNAL_SERVER_ERROR);
    }};

    @Override
    public ErrorCode apply(SetuErrorResponse setuErrorResponse) {
        Integer requestStatus = setuErrorResponse.getRequestStatus();
        return errorCodeMap.get(requestStatus);
    }
}
