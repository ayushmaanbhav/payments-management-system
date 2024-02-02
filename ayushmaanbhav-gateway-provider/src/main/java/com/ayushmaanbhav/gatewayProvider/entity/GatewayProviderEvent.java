package com.ayushmaanbhav.gatewayProvider.entity;

import com.ayushmaanbhav.commonsspring.db.pojo.AbstractVersionedPojo;
import com.ayushmaanbhav.gatewayProvider.embedded.ApiEventDetail;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class GatewayProviderEvent extends AbstractVersionedPojo {
    @Id
    @TableGenerator(name = "gatewayProviderEvent", pkColumnValue = "gatewayProviderEvent", initialValue = 1, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gatewayProviderEvent")
    Long id;

    @Embedded
    ApiEventDetail rawDetail;

    String orderId;
    String apiClientClass;
    String providerOrderId;
    String providerSessionId;
    String providerTransactionId;
    String providerStatus;
    String errorCode;
    String errorDescription;
}
