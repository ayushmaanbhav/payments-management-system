package com.ayushmaanbhav.commonsspring.api;

import com.ayushmaanbhav.commons.exception.ApiException;
import com.ayushmaanbhav.commonsspring.db.dao.ApplicationSettingDao;
import com.ayushmaanbhav.commonsspring.db.pojo.ApplicationSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationSettingApi extends AbstractApi{

    public static final String SMS_SERVICE_ENABLED = "sms.service.enabled";

    @Autowired
    private ApplicationSettingDao dao;

    @Transactional(rollbackFor = Exception.class)
    public ApplicationSetting saveOrUpdate(ApplicationSetting setting) throws ApiException {
        ApplicationSetting settingInDb = dao.select(setting.getKeyName());
        if (settingInDb == null) {
            settingInDb = create(setting.getKeyName(), setting.getValue());
        } else {
            settingInDb.setValue(setting.getValue());
            dao.update(settingInDb);
        }
        return settingInDb;
    }

    @Transactional(rollbackFor = Exception.class)
    public ApplicationSetting create(String keyName, String value) throws ApiException {
        ApplicationSetting setting = dao.select(keyName);
        verifyNonExistence(setting, "Setting with key already exists");
        setting = new ApplicationSetting();
        setting.setKeyName(keyName);
        setting.setValue(value);
        dao.save(setting);
        return setting;
    }

    @Transactional(rollbackFor = Exception.class)
    public ApplicationSetting get(String keyName) throws ApiException {
        ApplicationSetting setting = dao.select(keyName);
        verifyExistence(setting, "Application setting does not exist");
        return setting;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean getBoolean(String keyName) throws ApiException {
        ApplicationSetting setting = get(keyName);
        return Boolean.parseBoolean(setting.getValue());
    }

}
