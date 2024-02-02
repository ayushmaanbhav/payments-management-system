package com.ayushmaanbhav.commons.request;

import com.ayushmaanbhav.commons.contstants.ErrorMessage;
import com.ayushmaanbhav.commons.contstants.StoreType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class StoreRequest {

    private StoreType storeType;
    @NotNull(message = ErrorMessage.STORE_NAME_EMPTY_ERROR_MESSAGE)
    @Size(min = 3, message = ErrorMessage.STORE_NAME_TOO_SMALL_ERROR_MESSAGE)
    private String storeName;
    @NotNull(message = ErrorMessage.MOBILE_EMPTY_ERROR_MESSAGE)
    @Pattern(regexp = "^[0-9]{10}$", message = ErrorMessage.MOBILE_INVALID_ERROR_MESSAGE)
    private String mobile;

}
