package com.ayushmaanbhav.ledger.entity;

import com.ayushmaanbhav.commons.contstants.AccountOperationType;
import com.ayushmaanbhav.commons.contstants.Currency;
import com.ayushmaanbhav.commons.contstants.TransactionTransferEntityType;
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
@Table(name = "ledger_transaction_line_item")
public class TransactionLineItem extends AbstractVersionedPojo {
    @Id
    @TableGenerator(name = "transactionLineItem", pkColumnValue = "transactionLineItem", initialValue = 1, allocationSize = 100)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "transactionLineItem")
    Long id;

    @NonNull Long normalisedAmount;
    @NonNull @Enumerated(EnumType.STRING) Currency currency;
    @NonNull @Enumerated(EnumType.STRING) AccountOperationType operationType;
    @NonNull @Enumerated(EnumType.STRING) TransactionTransferEntityType transferEntityType;

    @ManyToOne @JoinColumn(name="account_id")
    @NonNull Account account;

    @ManyToOne @JoinColumn(name="transaction_id")
    @NonNull Transaction transaction;
}
