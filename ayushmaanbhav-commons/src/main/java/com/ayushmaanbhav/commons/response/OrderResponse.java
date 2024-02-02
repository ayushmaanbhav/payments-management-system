package com.ayushmaanbhav.commons.response;

import com.ayushmaanbhav.commons.contstants.DeliveryType;
import com.ayushmaanbhav.commons.contstants.OrderState;
import com.ayushmaanbhav.commons.contstants.PaymentMode;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class OrderResponse {

    private int id;
    private int fromPartyId;
    private int toPartyId;
    private OrderState state;
    private DeliveryType deliveryType;
    private int deliveryAddressId;
    private float deliveryFee;
    private List<OrderLineItemResponse> lineItems;
    private ZonedDateTime createdOn;
    private ZonedDateTime updatedOn;
    PaymentMode paymentMode;
    private CustomerResponse customer;
    private String paymentModeDetailsId;
    private String source;
    private String activePaymentOrderId;
    private String activePaymentOrderRequestId;
}
