package com.ayushmaanbhav.commons.response;

import com.ayushmaanbhav.commons.request.ProductMasterRequest;
import lombok.Data;

@Data
public class ProductMasterResponse extends ProductMasterRequest {

    private int id;
    private boolean hasVariant;

}
