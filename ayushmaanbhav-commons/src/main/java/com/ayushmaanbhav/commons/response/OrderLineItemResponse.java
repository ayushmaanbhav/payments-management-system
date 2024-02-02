package com.ayushmaanbhav.commons.response;

import com.ayushmaanbhav.commons.request.OrderLineItemRequest;
import lombok.Data;

@Data
public class OrderLineItemResponse extends OrderLineItemRequest {

    private int id;
    private int orderId;

}
