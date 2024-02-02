package com.ayushmaanbhav.commons.request;

import com.ayushmaanbhav.commons.contstants.DeliveryMode;
import com.ayushmaanbhav.commons.contstants.DeliveryType;
import com.ayushmaanbhav.commons.contstants.ErrorMessage;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Data
public class DeliveryOptionRequest {

    @Min(value = 0, message = ErrorMessage.DISTANCE_NEGATIVE_ERROR_MESSAGE)
    private int distance;
    private DeliveryType deliveryType;
    @Min(value = 0, message = ErrorMessage.ORDER_AMOUNT_NEGATIVE_ERROR_MESSAGE)
    private float minOrderAmount;
    @Min(value = 0, message = ErrorMessage.DELIVERY_FEE_NEGATIVE_ERROR_MESSAGE)
    private float deliveryFee;
    private DeliveryMode deliveryMode = DeliveryMode.STORE_MANAGED;
    @Valid
    private WeekScheduleRequest weekSchedule;

}
