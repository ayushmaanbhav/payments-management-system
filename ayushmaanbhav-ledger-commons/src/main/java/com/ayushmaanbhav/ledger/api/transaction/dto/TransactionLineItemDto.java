package com.ayushmaanbhav.ledger.api.transaction.dto;

import com.ayushmaanbhav.commons.contstants.AccountOperationType;
import com.ayushmaanbhav.commons.contstants.Currency;
import com.ayushmaanbhav.commons.contstants.TransactionTransferEntityType;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Jacksonized
public class TransactionLineItemDto {
    @NonNull String accountId;
    @NonNull AccountOperationType operationType;
    @NonNull TransactionTransferEntityType transferEntityType;
    @NonNull Long normalisedAmount;
    @NonNull Currency currency;
}
