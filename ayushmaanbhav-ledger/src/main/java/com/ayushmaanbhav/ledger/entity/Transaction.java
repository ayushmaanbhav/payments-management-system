package com.ayushmaanbhav.ledger.entity;

import com.ayushmaanbhav.commonsspring.db.pojo.AbstractVersionedPojo;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "ledger_transaction")
public class Transaction extends AbstractVersionedPojo {
    @Id
    @TableGenerator(name = "transaction", pkColumnValue = "transaction", initialValue = 1, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "transaction")
    Long id;

    @NonNull String transactionRefId;
    @NonNull ZonedDateTime transactionDate;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    @NonNull Set<TransactionLineItem> transactionLineItems;
}
