package com.ayushmaanbhav.commons.request;

import lombok.Data;

@Data
public class ProductUpdateRequest {

    private Float mrp;
    private Float sellingPrice;
    private Boolean isAvailable;

}
