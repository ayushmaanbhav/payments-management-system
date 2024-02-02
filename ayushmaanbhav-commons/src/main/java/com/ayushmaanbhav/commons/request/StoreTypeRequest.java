package com.ayushmaanbhav.commons.request;

import com.ayushmaanbhav.commons.contstants.StoreType;
import lombok.Data;

@Data
public class StoreTypeRequest {

    private StoreType storeType;
    private String displayName;
    private int imageId;

}
