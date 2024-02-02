package com.ayushmaanbhav.payment.entity;

import com.ayushmaanbhav.commons.contstants.*;
import com.ayushmaanbhav.commonsspring.db.pojo.AbstractVersionedPojo;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Set;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class PaymentOrder extends AbstractVersionedPojo {
    @Id
    @TableGenerator(name = "paymentOrder", pkColumnValue = "paymentOrder", initialValue = 1, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "paymentOrder")
    Long id;

    @NonNull String externalId;
    @NonNull @Enumerated(EnumType.STRING) PaymentOrderType type;
    @NonNull @Enumerated(EnumType.STRING) PaymentStatus status;
    //@NonNull PaymentOrderTypeConfig paymentOrderTypeConfig;

    @OneToMany(mappedBy = "paymentOrder", cascade = CascadeType.ALL)
    @NonNull Set<PaymentOrderLineItem> lineItems;

    // index fields
    String customerId;
    String storeId;
    @NonNull String source;
    @NonNull String sourceService;
}
