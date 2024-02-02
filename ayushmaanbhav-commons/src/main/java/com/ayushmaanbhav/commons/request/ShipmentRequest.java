package com.ayushmaanbhav.commons.request;

import com.ayushmaanbhav.commons.contstants.DeliveryMode;
import com.ayushmaanbhav.commons.contstants.DeliveryType;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class ShipmentRequest {

    private int deliveryOptionId;
    private ZonedDateTime shipDate;
    private Integer timeSlotId;
    List<ShipmentItemRequest> shipmentItems;

}
