package com.ayushmaanbhav.requestIdempotency.api;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.requestIdempotency.api.dto.ResolveRequestDto;
import com.ayushmaanbhav.requestIdempotency.api.dto.SaveRequestDto;
import com.ayushmaanbhav.service.commons.model.response.GenericResponse;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("requestIdempotency")
public interface RequestIdempotencyApi {

    @PostMapping
    void saveRequestMapping(@NonNull @RequestBody SaveRequestDto request) throws ApiException;

    @GetMapping
    @NonNull GenericResponse<ResolveRequestDto> resolveRequestMapping(@NonNull String requestId) throws ApiException;
}
