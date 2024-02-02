package com.ayushmaanbhav.commonsspring.db.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import java.time.ZonedDateTime;

@Data
@MappedSuperclass
public class AbstractVersionedPojo {
    @Version
    @JsonIgnore
    private int version;

    @JsonIgnore
    private ZonedDateTime createdOn = ZonedDateTime.now();

    @JsonIgnore
    private ZonedDateTime updatedOn = ZonedDateTime.now();

    @PreUpdate
    public void setUpdatedOn() {
        this.updatedOn = ZonedDateTime.now();
    }
}
