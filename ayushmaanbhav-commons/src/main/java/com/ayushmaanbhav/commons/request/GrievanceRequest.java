package com.ayushmaanbhav.commons.request;

import com.ayushmaanbhav.commons.contstants.ErrorMessage;
import com.ayushmaanbhav.commons.contstants.GrievanceType;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class GrievanceRequest {
    @NotNull(message = ErrorMessage.GRIEVANCE_MESSAGE_EMPTY_ERROR_MESSAGE)
    @Size(min = 5, message = ErrorMessage.GRIEVANCE_MESSAGE_TOO_SMALL_ERROR_MESSAGE)
    @Size(max = 255, message = ErrorMessage.GRIEVANCE_MESSAGE_TOO_LARGE_ERROR_MESSAGE)
    private String message;
    @Min(value = 0, message = ErrorMessage.ORDER_ID_INVALID_ERROR_MESSAGE)
    private int orderId;
    private GrievanceType grievanceType;
}
