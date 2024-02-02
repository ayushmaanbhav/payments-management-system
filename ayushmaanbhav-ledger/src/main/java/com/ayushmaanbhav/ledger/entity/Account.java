package com.ayushmaanbhav.ledger.entity;

import com.ayushmaanbhav.commons.contstants.AccountType;
import com.ayushmaanbhav.commons.contstants.Currency;
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
@Table(name = "ledger_account")
public class Account extends AbstractVersionedPojo {
    @Id
    @TableGenerator(name = "account", pkColumnValue = "account", initialValue = 1, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "account")
    Long id;

    @NonNull String requestId;
    @NonNull String externalId;
    @NonNull Currency currency;
    @NonNull @Enumerated(EnumType.STRING) AccountType type;
}
