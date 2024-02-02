package com.ayushmaanbhav.payment.entity;

import com.ayushmaanbhav.commons.contstants.*;
import com.ayushmaanbhav.commonsspring.db.pojo.AbstractVersionedPojo;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderConfig;
import com.ayushmaanbhav.gatewayProvider.entity.GatewayProviderPaymentDetail;
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
public class PaymentOrderLineItem extends AbstractVersionedPojo {
    @Id
    @TableGenerator(name = "paymentOrderLineItem", pkColumnValue = "paymentOrderLineItem", initialValue = 1, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "paymentOrderLineItem")
    Long id;

    @NonNull String externalId;
    @NonNull Long normalisedAmount;
    @NonNull @Enumerated(EnumType.STRING) Currency currency;
    @NonNull @Enumerated(EnumType.STRING) PaymentStatus status;
    ZonedDateTime paidDate;
    ZonedDateTime expiredDate;

    @ManyToOne
    @JoinColumn(name="gateway_provider_config_id")
    GatewayProviderConfig gatewayProviderConfig;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="gateway_provider_payment_detail_id")
    GatewayProviderPaymentDetail gatewayProviderPaymentDetail;

    @ManyToOne @JoinColumn(name="payment_order_id")
    @NonNull PaymentOrder paymentOrder;

    @NonNull String ledgerDebitAccountId;
    @NonNull String ledgerCreditAccountId;
}
