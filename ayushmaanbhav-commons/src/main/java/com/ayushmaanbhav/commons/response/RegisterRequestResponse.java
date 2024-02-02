package com.ayushmaanbhav.commons.response;

import com.ayushmaanbhav.commons.contstants.RegisterRequestStatus;
import com.ayushmaanbhav.commons.request.RegistrerRequestRequest;
import lombok.Data;

@Data
public class RegisterRequestResponse extends RegistrerRequestRequest {

    private int id;
    private RegisterRequestStatus status;

}
