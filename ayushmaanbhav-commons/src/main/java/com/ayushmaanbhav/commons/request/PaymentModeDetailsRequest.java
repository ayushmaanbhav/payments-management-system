package com.ayushmaanbhav.commons.request;

import com.ayushmaanbhav.commons.contstants.PaymentMode;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class PaymentModeDetailsRequest {
    private PaymentMode mode;
    private String gatewayProviderConfigId;
}
