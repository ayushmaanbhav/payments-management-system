package com.ayushmaanbhav.commons.response;

import com.ayushmaanbhav.commons.request.StoreRequest;
import lombok.Data;

@Data
public class StoreResponse extends StoreRequest {

    private int partyId;
    private int addressId;

}
