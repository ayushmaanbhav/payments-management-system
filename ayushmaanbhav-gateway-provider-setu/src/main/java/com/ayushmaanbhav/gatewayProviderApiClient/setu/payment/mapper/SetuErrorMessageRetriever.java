package com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.mapper;

import com.ayushmaanbhav.gatewayProviderApiClient.setu.payment.dto.error.SetuErrorResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SetuErrorMessageRetriever implements Function<SetuErrorResponse, String> {

    @Override
    public String apply(SetuErrorResponse setuErrorResponse) {
        return setuErrorResponse.getErrorData().getCode();
    }
}
