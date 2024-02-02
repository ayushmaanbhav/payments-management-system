package com.ayushmaanbhav.commons.request;

import com.ayushmaanbhav.commons.contstants.ErrorMessage;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegistrerRequestRequest {

    private String name;
    @NotNull(message = ErrorMessage.STORE_NAME_EMPTY_ERROR_MESSAGE)
    @Size(min = 3, message = ErrorMessage.STORE_NAME_TOO_SMALL_ERROR_MESSAGE)
    private String storeName;
    @NotNull(message = ErrorMessage.MOBILE_EMPTY_ERROR_MESSAGE)
    @Pattern(regexp = "^[0-9]{10}$", message = ErrorMessage.MOBILE_INVALID_ERROR_MESSAGE)
    private String mobile;
    @NotNull(message = ErrorMessage.PINCODE_EMPTY_ERROR_MESSAGE)
    @Size(min = 6, max = 6, message = ErrorMessage.PINCODE_SIZE_ERROR_MESSAGE)
    private String pincode;
    //Locality can be null when store is requesting. In future we may allow stores to select locality also
    private int localityId;
    @NotNull(message = ErrorMessage.ADDRESS_EMPTY_ERROR_MESSAGE)
    @Size(min = 5, message = ErrorMessage.ADDRESS_TOO_SMALL_ERROR_MESSAGE)
    @Size(max = 255, message = ErrorMessage.ADDRESS_TOO_LARGE_ERROR_MESSAGE)
    private String address;

}
