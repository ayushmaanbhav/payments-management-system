package com.ayushmaanbhav.gatewayProvider.entity;

import com.ayushmaanbhav.commons.contstants.PaymentStatus;
import com.ayushmaanbhav.commons.contstants.PaymentType;
import com.ayushmaanbhav.commonsspring.db.pojo.AbstractVersionedPojo;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class GatewayProviderPaymentDetail extends AbstractVersionedPojo {
    @Id
    @TableGenerator(name = "gatewayProviderPaymentOrderDetail", pkColumnValue = "gatewayProviderPaymentOrderDetail", initialValue = 1, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gatewayProviderPaymentOrderDetail")
    Long id;

    @NonNull String orderId;
    @NonNull String providerOrderId;
    @NonNull @Enumerated(EnumType.STRING) PaymentType type;

    String paymentWebUrl;
    String paymentDeepLink;
    String providerSessionId;
    String providerTransactionId;
    String providerPaymentMethod;
    String providerStatus;
    ZonedDateTime providerPaidDate;
    String providerErrorCode;
    String providerErrorDescription;
}
