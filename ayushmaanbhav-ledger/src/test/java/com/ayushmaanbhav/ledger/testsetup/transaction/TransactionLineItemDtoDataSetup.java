package com.ayushmaanbhav.ledger.testsetup.transaction;

import com.ayushmaanbhav.commons.contstants.AccountOperationType;
import com.ayushmaanbhav.commons.contstants.Currency;
import com.ayushmaanbhav.commons.contstants.TransactionTransferEntityType;
import com.ayushmaanbhav.ledger.api.transaction.dto.TransactionLineItemDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionLineItemDtoDataSetup {

    public static TransactionLineItemDto getTransactionLineItemDto(String accountId, Long normalisedAmount,
    AccountOperationType operationType, TransactionTransferEntityType entityType) {
        return TransactionLineItemDto.builder()
                .accountId(accountId)
                .operationType(operationType)
                .transferEntityType(entityType)
                .currency(Currency.INR)
                .normalisedAmount(normalisedAmount)
                .build();
    }
}
