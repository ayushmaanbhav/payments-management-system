package com.ayushmaanbhav.commons.response;

import com.ayushmaanbhav.commons.contstants.ReviewType;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class AggregateReviewResponse {
    private int id;
    private ReviewType reviewType;
    private int reviewTypeIdentifier;
    private float aggregateRating;
    private int reviewCount = 1;
}
