package com.ayushmaanbhav.commons.request;

import com.ayushmaanbhav.commons.contstants.ErrorMessage;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LocalityRequest {

    @NotNull(message = ErrorMessage.PINCODE_EMPTY_ERROR_MESSAGE)
    @Size(min = 6, max = 6, message = ErrorMessage.PINCODE_SIZE_ERROR_MESSAGE)
    private String zip;
    @NotNull(message = ErrorMessage.LOCALITY_NAME_EMPTY_ERROR_MESSAGE)
    @Size(min = 5, message = ErrorMessage.LOCALITY_NAME_TOO_SMALL_ERROR_MESSAGE)
    private String localityName;

}
