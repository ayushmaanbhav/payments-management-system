package com.ayushmaanbhav.commons.response;

import com.ayushmaanbhav.commons.contstants.PaymentMode;
import com.ayushmaanbhav.commons.request.PaymentModeDetailsRequest;
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
public class PaymentModeDetailsResponse {
    @NonNull String externalId;
    @NonNull private PaymentMode mode;
    @NonNull private String gatewayProviderConfigId;
}
