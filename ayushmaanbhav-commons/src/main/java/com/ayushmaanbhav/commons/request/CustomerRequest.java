package com.ayushmaanbhav.commons.request;

import com.ayushmaanbhav.commons.contstants.ErrorMessage;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CustomerRequest {

    @NotNull(message = ErrorMessage.CUSTOMER_NAME_EMPTY_ERROR_MESSAGE)
    @Size(min = 4, message = ErrorMessage.CUSTOMER_NAME_TOO_SHORT_ERROR_MESSAGE)
    private String customerName;
    @NotNull(message = ErrorMessage.MOBILE_EMPTY_ERROR_MESSAGE)
    @Size(min = 10, message = ErrorMessage.MOBILE_INVALID_ERROR_MESSAGE)
    private String mobile;

}
