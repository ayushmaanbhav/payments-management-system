package com.ayushmaanbhav.commons.request;

import com.ayushmaanbhav.commons.contstants.DiscountType;
import lombok.Data;

@Data
public class OfferDiscountRequest {

    private DiscountType type;
    private float discountValue;

}
