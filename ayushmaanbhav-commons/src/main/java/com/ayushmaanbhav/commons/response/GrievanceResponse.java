package com.ayushmaanbhav.commons.response;

import com.ayushmaanbhav.commons.request.GrievanceRequest;
import lombok.Data;

@Data
public class GrievanceResponse extends GrievanceRequest {
    private int id;
    private int fromPartyId;
}
