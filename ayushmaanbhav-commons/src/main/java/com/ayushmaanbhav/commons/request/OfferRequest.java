package com.ayushmaanbhav.commons.request;

import com.ayushmaanbhav.commons.contstants.OfferLevel;
import com.ayushmaanbhav.commons.contstants.OfferState;
import com.ayushmaanbhav.commons.contstants.OfferTargetType;
import com.ayushmaanbhav.commons.contstants.OfferType;
import lombok.Data;

@Data
public class OfferRequest {

    private OfferType type;
    private OfferTargetType targetType;
    private OfferLevel offerLevel;
    private OfferState state;
    private OfferDiscountRequest discount;
    private OfferEligibilityRequest eligibility;

}
