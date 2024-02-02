package com.ayushmaanbhav.commons.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ayushmaanbhav.commons.contstants.ErrorMessage;
import com.ayushmaanbhav.commons.serializer.PointDeserializer;
import com.ayushmaanbhav.commons.serializer.PointSerializer;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AddressRequest {

    @NotNull(message = ErrorMessage.PINCODE_EMPTY_ERROR_MESSAGE)
    @Size(min = 6, max = 6, message = ErrorMessage.PINCODE_SIZE_ERROR_MESSAGE)
    private String zip;
    @JsonSerialize(using = PointSerializer.class)
    @JsonDeserialize(using = PointDeserializer.class)
    private Point point;
    private int localityId;
    @NotNull(message = ErrorMessage.ADDRESS_EMPTY_ERROR_MESSAGE)
    @Size(min = 5, message = ErrorMessage.ADDRESS_TOO_SMALL_ERROR_MESSAGE)
    @Size(max = 255, message = ErrorMessage.ADDRESS_TOO_LARGE_ERROR_MESSAGE)
    private String addressLine;
    private String house;

}
