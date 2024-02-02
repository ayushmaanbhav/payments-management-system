package com.ayushmaanbhav.requestIdempotency.service;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.exception.ErrorCode;
import com.ayushmaanbhav.requestIdempotency.api.dto.ResolveRequestDto;
import com.ayushmaanbhav.requestIdempotency.api.dto.SaveRequestDto;
import com.ayushmaanbhav.requestIdempotency.entity.RequestIdempotencyDetail;
import com.ayushmaanbhav.requestIdempotency.entity.repository.RequestIdempotencyDetailRepository;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RequestIdempotencyService {
    RequestIdempotencyDetailRepository repository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveRequestMapping(@NonNull SaveRequestDto request) {
        repository.save(RequestIdempotencyDetail.builder()
                .externalId(request.getRequestId())
                .mappedId(request.getMappedId())
                .mappedIdType(request.getMappedIdType())
                .build());
    }

    public @NonNull ResolveRequestDto resolveRequestMapping(@NonNull String requestId) throws ApiException {
        RequestIdempotencyDetail requestDetail = repository.selectByExternalId(requestId);
        if (requestDetail == null) {
            throw new ApiException("Request not found", ErrorCode.NOT_FOUND);
        }
        return ResolveRequestDto.builder()
                .mappedId(requestDetail.getMappedId())
                .mappedIdType(requestDetail.getMappedIdType())
                .build();
    }
}
