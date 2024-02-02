package com.ayushmaanbhav.requestIdempotency.controller;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.requestIdempotency.api.RequestIdempotencyApi;
import com.ayushmaanbhav.requestIdempotency.api.dto.ResolveRequestDto;
import com.ayushmaanbhav.requestIdempotency.api.dto.SaveRequestDto;
import com.ayushmaanbhav.requestIdempotency.service.RequestIdempotencyService;
import com.ayushmaanbhav.service.commons.model.response.GenericResponse;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RequestIdempotencyController implements RequestIdempotencyApi {
    RequestIdempotencyService requestIdempotencyService;

    @Override
    public void saveRequestMapping(@NonNull SaveRequestDto request) throws ApiException {
        requestIdempotencyService.saveRequestMapping(request);
    }

    @Override
    public @NonNull GenericResponse<ResolveRequestDto> resolveRequestMapping(@NonNull String requestId) throws ApiException {
        return GenericResponse.fromData(requestIdempotencyService.resolveRequestMapping(requestId));
    }
}
