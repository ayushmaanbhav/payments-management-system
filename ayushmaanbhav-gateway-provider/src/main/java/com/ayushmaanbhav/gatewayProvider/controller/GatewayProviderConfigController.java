package com.ayushmaanbhav.gatewayProvider.controller;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayProviderConfigDto;
import com.ayushmaanbhav.gatewayProvider.service.GatewayProviderConfigService;
import com.ayushmaanbhav.service.commons.model.response.GenericResponse;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("gatewayProviderConfig")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GatewayProviderConfigController {
    GatewayProviderConfigService configService;

    @PreAuthorize("@adminPermissionService.verify()")
    @PostMapping
    public GenericResponse<String> create(@NonNull @RequestBody GatewayProviderConfigDto config) throws ApiException {
        return GenericResponse.fromData(configService.save(config));
    }
}
