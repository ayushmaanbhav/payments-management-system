package com.ayushmaanbhav.commons.request;

import lombok.Data;

@Data
public class SmsRequest {

    private String mobile;
    private String otp;

}
