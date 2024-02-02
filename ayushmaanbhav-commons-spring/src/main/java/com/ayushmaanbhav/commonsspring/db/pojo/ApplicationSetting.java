package com.ayushmaanbhav.commonsspring.db.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class ApplicationSetting extends AbstractVersionedPojo{

    @Id
    private String keyName;
    private String value;
}
