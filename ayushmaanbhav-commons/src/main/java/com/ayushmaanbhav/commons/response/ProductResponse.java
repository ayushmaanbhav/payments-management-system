package com.ayushmaanbhav.commons.response;

import com.ayushmaanbhav.commons.request.ProductRequest;
import lombok.Data;

@Data
public class ProductResponse extends ProductRequest {

    private int id;
    private int storePartyId;
    private boolean isAvailable;

}
