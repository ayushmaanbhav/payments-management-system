package com.ayushmaanbhav.commonsspring.controller;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.request.ApplicationSettingRequest;
import com.ayushmaanbhav.commons.response.ApplicationSettingResponse;
import com.ayushmaanbhav.commonsspring.dto.ApplicationSettingDtoApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(maxAge = 3600, allowCredentials = "true", originPatterns = "${origin.pattern}")
public class ApplicationSettingController {

    @Autowired
    private ApplicationSettingDtoApi api;

    @PreAuthorize("@permissionService.verify('ADMIN')")
    @PutMapping("setting")
    public ApplicationSettingResponse createOrUpdate(@RequestBody ApplicationSettingRequest request) throws ApiException {
        return api.createOrUpdate(request);
    }

}
