package com.ayushmaanbhav.commons.response;

import com.ayushmaanbhav.commons.contstants.PaymentMode;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
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
public class StoreConfigResponse {
    @NonNull private Integer storeId;
    @NonNull private Set<PaymentMode> paymentModes;
    @NonNull private List<PaymentModeDetailsResponse> paymentModeDetails;
    private Integer weekScheduleId;
}
