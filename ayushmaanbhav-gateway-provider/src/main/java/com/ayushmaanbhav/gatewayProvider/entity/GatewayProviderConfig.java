package com.ayushmaanbhav.gatewayProvider.entity;

import com.ayushmaanbhav.commons.contstants.GatewayProvider;
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
public class GatewayProviderConfig extends AbstractVersionedPojo {
    @Id
    @TableGenerator(name = "gatewayProviderConfig", pkColumnValue = "gatewayProviderConfig", initialValue = 1, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gatewayProviderConfig")
    Long id;

    @NonNull String externalId;
    @NonNull @Enumerated(EnumType.STRING) GatewayProvider provider;
    @NonNull String merchantId;

    @ManyToOne(cascade = CascadeType.ALL) @JoinColumn(name = "connection_setting_id")
    @NonNull GatewayConnectionSetting connectionSetting;

    @NonNull Boolean disabled;
    String disabledReason;
    ZonedDateTime disabledDate;
}
