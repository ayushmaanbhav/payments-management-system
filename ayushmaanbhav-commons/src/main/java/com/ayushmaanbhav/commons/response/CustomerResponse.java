package com.ayushmaanbhav.commons.response;

import com.ayushmaanbhav.commons.request.CustomerRequest;
import lombok.Data;

@Data
public class CustomerResponse extends CustomerRequest {

    private int partyId;

}
