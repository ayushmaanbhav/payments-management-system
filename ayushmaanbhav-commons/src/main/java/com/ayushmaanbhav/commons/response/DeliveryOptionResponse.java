package com.ayushmaanbhav.commons.response;

import com.ayushmaanbhav.commons.request.DeliveryOptionRequest;
import lombok.Data;

@Data
public class DeliveryOptionResponse extends DeliveryOptionRequest {

    private int id;
    private int storePartyId;
    private Integer weekScheduleId;

}
