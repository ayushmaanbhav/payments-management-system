package com.ayushmaanbhav.commons.request;

import com.ayushmaanbhav.commons.contstants.DeliveryType;
import com.ayushmaanbhav.commons.contstants.ErrorMessage;
import com.ayushmaanbhav.commons.contstants.OrderState;
import com.ayushmaanbhav.commons.contstants.PaymentMode;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Data
public class OrderRequest {

    @Min(value = 0, message = ErrorMessage.PARTY_ID_INVALID_ERROR_MESSAGE)
    private int fromPartyId;
    private OrderState state;
    private DeliveryType deliveryType;
    @Min(value = 0, message = ErrorMessage.DELIVERY_ADDRESS_INVALID_ERROR_MESSAGE)
    private int deliveryAddressId;
    @Min(value = 0, message = ErrorMessage.DELIVERY_FEE_NEGATIVE_ERROR_MESSAGE)
    private float deliveryFee;
    private Integer offerId;
    //Value set to COD is for backward compatibility
    private PaymentMode paymentMode;
    private String paymentModeDetailsId;
    private String source; // web or app
    @Valid
    private List<OrderLineItemRequest> lineItems;
    private List<ShipmentRequest> shipments;

}
