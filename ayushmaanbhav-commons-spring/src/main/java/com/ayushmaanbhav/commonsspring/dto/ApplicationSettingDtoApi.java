package com.ayushmaanbhav.commonsspring.dto;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commons.request.ApplicationSettingRequest;
import com.ayushmaanbhav.commons.response.ApplicationSettingResponse;
import com.ayushmaanbhav.commons.util.ConvertUtil;
import com.ayushmaanbhav.commonsspring.api.ApplicationSettingApi;
import com.ayushmaanbhav.commonsspring.db.pojo.ApplicationSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationSettingDtoApi {

    @Autowired
    private ApplicationSettingApi api;

    public ApplicationSettingResponse createOrUpdate(ApplicationSettingRequest request) throws ApiException {
        ApplicationSetting setting = ConvertUtil.convert(request, ApplicationSetting.class);
        setting = api.saveOrUpdate(setting);
        return ConvertUtil.convert(setting, ApplicationSettingResponse.class);
    }

}
