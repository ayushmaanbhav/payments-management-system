package com.ayushmaanbhav.commons.response;

import com.ayushmaanbhav.commons.contstants.DeliveryMode;
import com.ayushmaanbhav.commons.contstants.DeliveryType;
import com.ayushmaanbhav.commons.contstants.ShipmentState;
import com.ayushmaanbhav.commons.request.ShipmentRequest;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class ShipmentResponse {

    private int id;
    private int orderId;
    private String externalId;
    private ShipmentState state;
    private int toAddressId;
    private DeliveryType deliveryType;
    private DeliveryMode deliveryMode;
    private ZonedDateTime shipAfter;
    private ZonedDateTime shipBefore;
    private Integer timeSlotId;
    private Float deliveryFee;
    private int externalShipmentId;

    private List<ShipmentItemResponse> shipmentItems;

}
