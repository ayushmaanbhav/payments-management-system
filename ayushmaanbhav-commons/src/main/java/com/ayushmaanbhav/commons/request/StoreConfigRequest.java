package com.ayushmaanbhav.commons.request;

import com.ayushmaanbhav.commons.contstants.PaymentMode;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class StoreConfigRequest {

    private int storeId;
    private Set<PaymentMode> paymentModes;
    private List<PaymentModeDetailsRequest> paymentModeDetails;
}
