package com.ayushmaanbhav.commonsspring.db.dao;

import com.ayushmaanbhav.commonsspring.db.pojo.ApplicationSetting;
import org.springframework.stereotype.Repository;

@Repository
public class ApplicationSettingDao extends AbstractDao<ApplicationSetting>{
    public ApplicationSettingDao() {
        super(ApplicationSetting.class);
    }

}
