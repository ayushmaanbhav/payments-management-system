package com.ayushmaanbhav.gatewayProvider.entity;

import com.ayushmaanbhav.commons.contstants.GatewayProvider;
import com.ayushmaanbhav.commonsspring.db.pojo.AbstractVersionedPojo;
import com.ayushmaanbhav.gatewayProvider.dto.ConnectionSettingDto;
import com.ayushmaanbhav.gatewayProvider.entity.encryption.ConnectionSettingEncryptor;
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
public class GatewayConnectionSetting extends AbstractVersionedPojo {
    @Id
    @TableGenerator(name = "gatewayConnectionSetting", pkColumnValue = "gatewayConnectionSetting", initialValue = 1, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gatewayConnectionSetting")
    Long id;

    @NonNull String externalId;
    @NonNull @Enumerated(EnumType.STRING) GatewayProvider provider;

    @Convert(converter = ConnectionSettingEncryptor.class)
    @Column(name = "connection_setting", columnDefinition = "varchar(4096)")
    @NonNull ConnectionSettingDto connectionSetting;

    @NonNull Boolean tokenRefreshEnabled;
    @NonNull Integer retryCount;
    ZonedDateTime lastTokenRefreshDate;
}
