package com.ayushmaanbhav.commons.mapper;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import lombok.NonNull;

public interface OneWayMapper<I, O> extends Mapper<I, O> {

    @Override
    default @NonNull I reverse(@NonNull O input) throws ApiException {
        throw new ApiException("reverse mapper not implemented", ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
