package com.ayushmaanbhav.commons.request;

import com.ayushmaanbhav.commons.contstants.ErrorMessage;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class OrderLineItemRequest {

    @Min(value = 1, message = ErrorMessage.PRODUCT_ID_INVALID_ERROR_MESSAGE)
    private int productId;
    @Min(value = 0, message = ErrorMessage.SELLING_PRICE_NEGATIVE_ERROR_MESSAGE)
    private float sellingPrice;
    @Min(value = 1, message = ErrorMessage.ORDER_QUANTITY_ZERO_ERROR_MESSAGE)
    private int orderedQuantity;
    @Min(value = 0, message = ErrorMessage.ACCEPTED_QUANTITY_NEGATIVE_ERROR_MESSAGE)
    private int acceptedQuantity;
    @Min(value = 0, message = ErrorMessage.FULFILLED_QUANTITY_NEGATIVE_ERROR_MESSAGE)
    private int fulfilledQuantity;

}
