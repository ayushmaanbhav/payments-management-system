package com.ayushmaanbhav.commons.response;

import com.ayushmaanbhav.commons.request.ReviewRequest;
import lombok.Data;

@Data
public class ReviewResponse extends ReviewRequest {
    private int id;
    private int toPartyId;
    private int reviewTypeIdentifier;
}
