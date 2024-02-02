package com.ayushmaanbhav.gatewayProvider.controller;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.gatewayProvider.dto.GatewayConnectionSettingDto;
import com.ayushmaanbhav.gatewayProvider.service.GatewayConnectionSettingService;
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
@RequestMapping("gatewayConnectionSetting")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GatewayConnectionSettingController {
    GatewayConnectionSettingService settingService;

    @PostMapping
    @PreAuthorize("@adminPermissionService.verify()")
    public GenericResponse<String> create(@NonNull @RequestBody GatewayConnectionSettingDto setting) throws ApiException {
        return GenericResponse.fromData(settingService.save(setting));
    }
}
