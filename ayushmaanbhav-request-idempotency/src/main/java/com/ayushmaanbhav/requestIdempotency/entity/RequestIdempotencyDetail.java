package com.ayushmaanbhav.requestIdempotency.entity;

import com.ayushmaanbhav.commonsspring.db.pojo.AbstractVersionedPojo;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class RequestIdempotencyDetail extends AbstractVersionedPojo {
    @Id
    @TableGenerator(name = "requestIdempotencyDetail", pkColumnValue = "requestIdempotencyDetail", initialValue = 1, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "requestIdempotencyDetail")
    Long id;

    @NonNull String externalId;
    @NonNull String mappedId;
    @NonNull String mappedIdType;
}
